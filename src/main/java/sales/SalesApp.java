package sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

	public void generateSalesActivityReport(String salesId, int maxRow, boolean isNatTrade, boolean isSupervisor) {

		Sales sales = getSales(salesId);

		if (sales == null) return;

		SalesReportDao salesReportDao = new SalesReportDao();
		List<SalesReportData> reportDataList = salesReportDao.getReportData(sales);

		List<String> headers = getHeaders(isNatTrade);

		SalesActivityReport report = this.generateReport(headers, reportDataList);

		if (report == null) {
			return;
		}
		uploadDocument(report);

	}

	protected void uploadDocument(SalesActivityReport report) {
		EcmService ecmService = new EcmService();
		ecmService.uploadDocument(report.toXml());
	}

	protected List<String> getHeaders(boolean isNatTrade) {
		if (isNatTrade) {
			return Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
		} else {
			return Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
		}
	}

	protected Sales getSales(String salesId) {
		Sales sales = getSalesDao().getSalesBySalesId(salesId);

		Date today = new Date();
		if (sales==null||today.after(sales.getEffectiveTo())
				|| today.before(sales.getEffectiveFrom())) {
			return null;
		}
		return sales;
	}

	protected SalesDao getSalesDao() {
		return new SalesDao();
	}

	protected SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
		// TODO Auto-generated method stub
		return null;
	}

	protected SalesReportDao getSalesReportDao() {
		return new SalesReportDao();
	}

}
