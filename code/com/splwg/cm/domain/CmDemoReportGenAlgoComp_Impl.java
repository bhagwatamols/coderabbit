package com.splwg.cm.domain;

import java.time.LocalDate;
import java.util.Locale;

import com.splwg.ccb.domain.reportsubmission.FOPReportGenerationComponent_Impl;
import com.splwg.ccb.domain.reportsubmission.ReportDataExtractAlgorithmSpot;
import com.splwg.ccb.domain.reportsubmission.ReportParameters;

/**
 * @author AmolBhagwat
 *
@AlgorithmComponent ()
 */
public class CmDemoReportGenAlgoComp_Impl extends CmDemoReportGenAlgoComp_Gen
		implements ReportDataExtractAlgorithmSpot {

	 private String messageCat = "30";
	 private String messageNumber = "2012";
	 private String errorMessageNumber = "2011";
	 private String email ="amol.bhagwat@riaadvisory.com"; 
	 private boolean reportStatusFlag = false;
	 private String reportName =null;
	 private String reportCode=null;
	 String reportMode=null;
		String outputFormat=null;
		String rportTemplate=null;
		String reportTypeTemp=null;
		String scriptCode=null;
		ReportParameters reportParam=null;
	 
	@Override
	public void invoke() {
		String sourceXML = "";
		String xmlOpeningtag = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<DOCUMENT xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" TYPE=\"RPWIP\" VERSION=\"11.5\"><DOCSET>";
		String xmlClosingTag = "</DOCSET></DOCUMENT>";
		reportName="demo_test";
		sourceXML = xmlOpeningtag + " " + getReportinfo() + " " + xmlClosingTag;
		
		
		 if (this.outputFormat.equals("PDF") || this.outputFormat.equals("XLS")) {
			         generateReport(this.reportTypeTemp, sourceXML, this.reportName + "_" + 
			            LocalDate.now().toString().replaceAll(":", "-") + "." + this.outputFormat
			             .toLowerCase(Locale.ENGLISH));
			       } 

	}
	
	private void generateReport(String xslPath, String xmlContent, String reportPath) {
		     FOPReportGenerationComponent_Impl fopReportGenerationComponent = new FOPReportGenerationComponent_Impl();
		     try {
		      fopReportGenerationComponent.generateReport(xslPath, xmlContent, reportPath);
		     this.reportName = fopReportGenerationComponent.getReportName();
		      this.reportStatusFlag = true;
		     } catch (Exception exception) {
		       this.messageNumber = this.errorMessageNumber;
		      this.reportStatusFlag = false;
		    } 
		  }
	
	private String getReportinfo()
	{
		StringBuilder reportString = new StringBuilder();
		reportString.append("<TESTREPORT>")
		.append("<REPORTHEADER> <FROMDATE>01-JAN-2020</FROMDATE> <TODATE>31-JAN-2020</TODATE>  </REPORTHEADER>")
		
		.append("<REPORTDETAIL> <PERSONNAME>ABC</PERSONNAME> <PERSONAGE>30</PERSONAGE> </REPORTDETAIL>")
		.append("<REPORTDETAIL> <PERSONNAME>PQR</PERSONNAME> <PERSONAGE>33</PERSONAGE> </REPORTDETAIL>")
		.append("<REPORTDETAIL> <PERSONNAME>XYZ</PERSONNAME> <PERSONAGE>34</PERSONAGE> </REPORTDETAIL>")
		
		.append("</TESTREPORT>");
		
		
		return reportString.toString();
	}

	@Override
	public String getEmailId() {
		
		return this.email;
	}

	@Override
	public String getMessageCategory() {
		// TODO Auto-generated method stub
		return this.messageCat;
	}

	@Override
	public String getMessageNumber() {
		// TODO Auto-generated method stub
		return  this.messageNumber;
	}

	@Override
	public String getReportName() {
		// TODO Auto-generated method stub
		return this.reportName;
	}

	@Override
	public boolean getreportStatusFlag() {
		// TODO Auto-generated method stub
		return this.reportStatusFlag;
	}

	@Override
	public void setReportCode(String reportCode) {
		
		this.reportCode=reportCode;

	}

	@Override
	public void setReportMode(String reportMode) {
		this.reportMode=reportMode;

	}
	
	@Override
	public void setReportTemplate(String rportTemplate) {
		this.rportTemplate=rportTemplate;

	}

	@Override
	public void setReportTypeTemplate(String reportTypeTemp) {
		this.reportTypeTemp=reportTypeTemp;

	}

	@Override
	public void setScriptCode(String scriptCode) {
		this.scriptCode=scriptCode;

	}

	@Override
	public void setreportOutptFormat(String outputFormat) {
		this.outputFormat=outputFormat;

	}

	@Override
	public void setreportParameters(ReportParameters reportParam) {
		this.reportParam=reportParam;

	}

}
