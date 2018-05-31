package org.pengisgood.services;

import net.sf.jasperreports.engine.*;
import org.pengisgood.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

import static java.io.File.createTempFile;
import static java.util.Arrays.asList;

@Service
public class JasperReportService {

    @Autowired
    private ApplicationContext appContext;

    private static final String TEST_REPORT_URL = "/jasperTemplates/TableDemo.jrxml";

    public ModelAndView generatePdf() {
        Map<String, Object> params = new HashMap<>();
        ShoppingCart shoppingCart = ShoppingCart.builder()
            .itemGroups(asList(
                ItemGroup.builder()
                    .groupName("M1001158003    保养菜单-基础B保养(AMG机油)")
                    .subtotal(999.96F)
                    .items(asList(
                        Item.builder().code("00115801").desc("进行附加组件的B类保养范围").discount(0.85F).price(9389.32F).build(),
                        Item.builder().code("MA2780940004").desc("进行附加组件的B类保养范围").discount(0.85F).price(9389.32F)
                            .build()
                    ))
                    .build(),
                ItemGroup.builder()
                    .groupName("C235492")
                    .subtotal(999.96F)
                    .items(asList(
                        Item.builder().code("553465654").desc("进行附加组件的B类保养范围").discount(0.85F).price(9389.32F).build(),
                        Item.builder().code("DA2339894234").desc("进行附加组件的B类保养范围").discount(0.85F).price(9389.32F)
                            .build()
                    ))
                    .build()
            ))
            .build();

        params.put("datasource", asList(shoppingCart));

        return generateReport(TEST_REPORT_URL, params);
    }

    private ModelAndView generateReport(String templateUrl, Map<String, Object> params) {
        JasperReportsPdfView view = new JasperReportsPdfView();
        compileReport(view, templateUrl);

        view.setApplicationContext(appContext);
        return new ModelAndView(view, params);
    }

    private void compileReport(JasperReportsPdfView view, String templateUrl) {
        try (InputStream inputStream = getClass().getResourceAsStream(templateUrl)) {
            File reportTempFile = createTempFile("report_temp", ".jasper");
            JasperReport report = JasperCompileManager.compileReport(inputStream);

            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(bos)) {

                out.writeObject(report);

                Path path = Paths.get(reportTempFile.getAbsolutePath());
                Files.write(path, bos.toByteArray());

                view.setUrl(reportTempFile.toURI().toURL().toString());
            }
        } catch (IOException | JRException ex) {
            throw new RuntimeException();
        }
    }
}
