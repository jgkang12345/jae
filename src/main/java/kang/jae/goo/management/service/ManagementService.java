package kang.jae.goo.management.service;

import kang.jae.goo.config.SecurityConfig;
import kang.jae.goo.config.utils.SecurityUtils;
import kang.jae.goo.management.dto.Excel_Upload_DTO;
import kang.jae.goo.management.mapper.ManagementMapper;
import kang.jae.goo.register.dto.TransactionSlipDTO;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class ManagementService {
    private final ManagementMapper managementMapper;

    public ManagementService(ManagementMapper managementMapper) {
        this.managementMapper = managementMapper;
    }

    public Map<String, Object> doSearch(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        try {
            map.put("user_id", SecurityUtils.CurrentUserName());
            List<TransactionSlipDTO> list = managementMapper.doSearch(map);
            result.put("data", list);
            result.put("result", true);
        }
        catch (Exception e) {
            e.printStackTrace();
            result.put("result", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    public Map<String, Object> deleteItem(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        try {
            managementMapper.deleteItem(map);
            result.put("result", true);
        }
        catch (Exception e) {
            e.printStackTrace();
            result.put("result", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    public Map<String, Object> updateItem(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        try {
            map.put("user_id", SecurityUtils.CurrentUserName());
            managementMapper.updateItem(map);
            result.put("result", true);
        }
        catch (Exception e) {
            e.printStackTrace();
            result.put("result", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    public Map<String, Object> selectItemSetting() {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> map =new HashMap<>();
        try {
            map.put("user_id", SecurityUtils.CurrentUserName());
            List<Map<String,Object>> list = managementMapper.selectItemSetting(map);
            result.put("data", list);
            result.put("result", true);
        }
        catch (Exception e) {
            e.printStackTrace();
            result.put("result", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    public void excelDownload(Map<String, Object> map, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");
        int rowNum = 0;
        XSSFRow xssfRow;
        XSSFCell xssfCell;
        XSSFWorkbook xssfWb = new XSSFWorkbook(); //XSSFWorkbook 객체 생성
        XSSFSheet xssfSheet = xssfWb.createSheet("sheet1"); // 워크시트 이름 설정

        XSSFFont font = xssfWb.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL); // 폰트 스타일
        font.setFontHeightInPoints((short)20); // 폰트 크기
        font.setBold(true); // Bold 설정

        CellStyle tableCellStyle = xssfWb.createCellStyle();
        tableCellStyle.setBorderTop((short) 1);    // 테두리 위쪽
        tableCellStyle.setBorderBottom((short) 1); // 테두리 아래쪽
        tableCellStyle.setBorderLeft((short) 1);   // 테두리 왼쪽
        tableCellStyle.setBorderRight((short) 1);  // 테두리 오른쪽

        xssfSheet.setColumnWidth(0, (xssfSheet.getColumnWidth(0))+(short)2048);
        xssfSheet.setColumnWidth(1, (xssfSheet.getColumnWidth(0))+(short)2048);
        xssfSheet.setColumnWidth(2, (xssfSheet.getColumnWidth(0))+(short)2048);
        xssfSheet.setColumnWidth(3, (xssfSheet.getColumnWidth(0))+(short)2048);

        String header [] = {"거래일", "계정과목", "금액", "수입/지출"};
        xssfRow = xssfSheet.createRow(rowNum++);
        for (int i = 0; i < header.length; i++) {
            xssfCell = xssfRow.createCell(i);
            xssfCell.setCellStyle(tableCellStyle);
            xssfCell.setCellValue(header[i]);
        }

        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> target = list.get(i);
            xssfRow = xssfSheet.createRow(rowNum++);
            for (int j = 0; j <  header.length; j++) {
                xssfCell = xssfRow.createCell(j);
                switch (j) {
                    case 0:
                        xssfCell.setCellStyle(tableCellStyle);
                        xssfCell.setCellValue(target.get("transactionOkDate").toString());
                        break;
                    case 1:
                        xssfCell.setCellStyle(tableCellStyle);
                        xssfCell.setCellValue(target.get("title").toString());
                        break;
                    case 2:
                        xssfCell.setCellStyle(tableCellStyle);
                        xssfCell.setCellValue( comma((int)target.get("amount")) );
                        break;

                    case 3:
                        xssfCell.setCellStyle(tableCellStyle);
                        xssfCell.setCellValue( target.get("trade_cd").equals("O") ? "지출" : "수입" );
                        break;
                }
            }
        }
        String random = UUID.randomUUID().toString();
        String excelYYMMDD = String.valueOf(System.currentTimeMillis());
        response.setContentType("application/xlsx");
        response.setHeader("Content-Disposition", "ATTachment; Filename=" + random +"_"+ excelYYMMDD + ".xlsx");
        response.setHeader("X-Filename", random +"_"+ excelYYMMDD + ".xlsx");

        OutputStream fileOut = response.getOutputStream();
        xssfWb.write(fileOut);
        fileOut.close();;

        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    private String comma(int value) {
        DecimalFormat df = new DecimalFormat("###,###");
        String value_str = df.format(value);
        return value_str;
    }

    public Map<String, Object> mg_excelUpload(Excel_Upload_DTO dto) {
        Map<String, Object> result = new HashMap<>();
        try
        {
            String orgFileName = dto.getFile().getOriginalFilename();
            String fileExt = orgFileName.substring(orgFileName.indexOf('.') + 1);

            String tempFileName = "special_note_" + UUID.randomUUID().toString();
            File savePath = new File(  "C\\" + tempFileName + "." + fileExt);
            dto.getFile().transferTo(savePath);

            Workbook wb = WorkbookFactory.create(savePath, null, true);
            Sheet sh = wb.getSheetAt(0);
            int rows = sh.getLastRowNum() + 1;
            int cols = 0;
            int header_cols = 0;

            List<Map<String, Object>> list = new ArrayList<>();

            if (rows > 1) {
                header_cols = sh.getRow(0).getLastCellNum();
                cols = header_cols;

                if (cols != 4) {
                    result.put("result", false);
                    return result;
                }

                for (int row = 1; row < rows; row++) {
                    Map<String, Object> temp = new HashMap<>();
                    for (int i =0; i < cols; i++) {
                        String data = getCellText(sh.getRow(row).getCell(i)).trim();
                        switch (i) {
                            case 0:
                                temp.put("transactionOkDate", data);
                                break;
                            case 1:
                                temp.put("transactionOkDate", data);
                                break;
                            case 2:
                                temp.put("transactionOkDate", data);
                                break;

                            case 3:
                                temp.put("transactionOkDate", data);
                                break;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result.put("result", false);
        }
        return result;
    }

    private String getCellText(Cell cell) throws Exception {

        String cellText = "";
        BigDecimal cellValue = new BigDecimal(0);

        if (cell != null) {
            switch (cell.getCellType()) {

                case Cell.CELL_TYPE_STRING:

                    cellText = cell.getRichStringCellValue().getString();

//				int dataFormat = cell.getCellStyle().getDataFormat();
//				String dataFormatString = cell.getCellStyle().getDataFormatString();
//
//				// 스미후루 B/L No.
//			    if(dataFormat == 191 && "\"MCPU\"@".equals(dataFormatString)) {
//			    	CellTextFormatter txtFmt = new CellTextFormatter(dataFormatString);
//			    	cellText = txtFmt.format(cellText);
//			    }

                    break;

                // case HSSFCell.CELL_TYPE_NUMERIC:
                case Cell.CELL_TYPE_NUMERIC:

                    cellValue = new BigDecimal(cell.getNumericCellValue());
                    cellText = String.valueOf(cellValue.setScale(6, RoundingMode.HALF_DOWN).stripTrailingZeros());

//				cellText = String.valueOf(cell.getNumericCellValue());
                    if (cellText.endsWith(".0") || cellText.endsWith(".00")) {
                        cellText = cellText.substring(0, cellText.lastIndexOf("."));
                    } else if (cellText.indexOf("E") != -1) {
//					BigDecimal cellValue = new BigDecimal(cell.getNumericCellValue());
                        cellText = cellValue.setScale(2, RoundingMode.HALF_DOWN).toString();
                        if (cellText.endsWith(".0") || cellText.endsWith(".00")) {
                            cellText = cellText.substring(0, cellText.lastIndexOf("."));
                        }
                    }
                    break;

                // case HSSFCell.CELL_TYPE_FORMULA:
                case Cell.CELL_TYPE_FORMULA:
                    switch (cell.getCachedFormulaResultType()) {
                        // case Cell.CELL_TYPE_NUMERIC:
                        case Cell.CELL_TYPE_NUMERIC:

                            cellValue = new BigDecimal(cell.getNumericCellValue());
                            cellText = String.valueOf(cellValue.setScale(6, RoundingMode.HALF_UP).stripTrailingZeros());

//					cellText = String.valueOf(cell.getNumericCellValue());
                            if (cellText.endsWith(".0") || cellText.endsWith(".00")) {
                                cellText = cellText.substring(0, cellText.lastIndexOf("."));
                            } else if (cellText.indexOf("E") != -1) {
//						cellValue = new BigDecimal(cell.getNumericCellValue());
                                cellText = cellValue.setScale(2, RoundingMode.HALF_DOWN).toString();
                                if (cellText.endsWith(".0") || cellText.endsWith(".00")) {
                                    cellText = cellText.substring(0, cellText.lastIndexOf("."));
                                }
                            }
                            break;
                        // case Cell.CELL_TYPE_STRING:
                        case Cell.CELL_TYPE_STRING:
                            cellText = cell.getRichStringCellValue().getString();
                            break;
                    }
                    break;

                default:
                    cellText = "";
            }
        }

        return cellText;
    }

}
