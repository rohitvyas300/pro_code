import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.hssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

		public class WriteExcel {

			public FileInputStream fis = null;
		    public FileOutputStream fos = null;
		    public HSSFWorkbook workbook = null;
		    public HSSFSheet sheet = null;
		    public HSSFRow row = null;
		    public HSSFCell cell = null;
		    String xlFilePath;	
		 
		    public boolean setCellData(String sheetName, String colName, int rowNum, String value) throws IOException
		    {
		    	this.xlFilePath = "D:\\medi\\pro_code\\Merck\\product.xls";
		        fis = new FileInputStream(xlFilePath);
		        workbook = new HSSFWorkbook(fis);
		        fis.close();
		        try
		        {
		            int col_Num = -1;
		            sheet = workbook.getSheet(sheetName);
		 
		            row = sheet.getRow(0);
		            for (int i = 0; i < row.getLastCellNum(); i++) {
		                if (row.getCell(i).getStringCellValue().trim().equals(colName))
		                {
		                    col_Num = i;
		                }
		            }
		 
		            sheet.autoSizeColumn(col_Num);
		            row = sheet.getRow(rowNum - 1);
		            if(row==null)
		                row = sheet.createRow(rowNum - 1);
		 
		            cell = row.getCell(col_Num);
		            if(cell == null)
		                cell = row.createCell(col_Num);
		 
		            cell.setCellValue(value);
		 
		            fos = new FileOutputStream(xlFilePath);
		            workbook.write(fos);
		            fos.close();
		        }
		        catch (Exception ex)
		        {
		            ex.printStackTrace();
		            return  false;
		        }
		        return true;
		    }
		    
//		    public static void main(String args[]) throws Exception
//		    {
//		    	WriteExcel eat = new WriteExcel();
//		        eat.setCellData("Product","Product Name",2,"ChromeDriver has not been");
//		    }
		    
		} //EOD Class
	
	
	


