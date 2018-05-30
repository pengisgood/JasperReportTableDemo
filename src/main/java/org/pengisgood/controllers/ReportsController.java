package org.pengisgood.controllers;

import org.pengisgood.services.JasperReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/")
public class ReportsController {

  @Autowired
  private JasperReportService reportService;

  @RequestMapping(value = "/pdf", method = GET)
  @ResponseBody
  public ModelAndView getPdf() {
    return reportService.generatePdf();
  }
}
