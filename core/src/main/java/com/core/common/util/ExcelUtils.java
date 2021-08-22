package com.core.common.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.http.HttpRequest;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import reactor.netty.http.server.HttpServerRequest;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static org.apache.poi.hssf.record.cf.BorderFormatting.BORDER_MEDIUM;
import static org.apache.poi.hssf.record.cf.BorderFormatting.BORDER_THIN;

@Component("excelUtils")
public class ExcelUtils {

    public static Workbook convertListToXls(List resultList, String fileName ){
        // Create a EXCEL
        Workbook wb = new HSSFWorkbook();
        // Create a SHEET
        Sheet sheet1 = wb.createSheet (fileName);
        if(resultList!=null){
            for (int i = 0; i < resultList.size(); i++) {
                // Create a row
                Row row = sheet1.createRow(i);
                List rowList=(List)resultList.get(i);
                for (int j = 0; j < rowList.size(); j++) {
                    Cell cell = row.createCell(j);
                    String cellLiString=(String)rowList.get(j);
                    cell.setCellValue(cellLiString );
                }
            }
        }
        return wb;
    }

    public static String  saveWorkbookToFile(Workbook workbook) throws Exception {
        String filePath = "./excel/" + workbook.getSheetName(0) + ".xls";
        FileOutputStream fileOut =
            new FileOutputStream(filePath);
        workbook.write(fileOut);
        fileOut.close();
        return filePath;
    }


    private  String excelDir; //서버에서

    /**
     * 대용량 엑셀
     * @param zipfile the template file
     * @param tmpfile the XML file with the sheet data
     * @param entry the name of the sheet entry to substitute, e.g. xl/worksheets/sheet1.xml
     * @param out the stream to write the result to
     */
    public void substitute(File zipfile, Map sheetMap, OutputStream out)
            throws IOException {

        ZipFile zip = new ZipFile(zipfile);

        ZipOutputStream zos = new ZipOutputStream(out);
        Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zip.entries();
        while (en.hasMoreElements()) {
            ZipEntry ze = en.nextElement();
            if (!sheetMap.containsKey(ze.getName())) {
                zos.putNextEntry(new ZipEntry(ze.getName()));
                InputStream is = zip.getInputStream(ze);
                copyStream(is, zos);
                is.close();
            }
        }

        Iterator it = sheetMap.keySet().iterator();
        while (it.hasNext()) {
            String entry = (String) it.next();
            System.out.println(entry);
            zos.putNextEntry(new ZipEntry(entry));
            InputStream is = new FileInputStream((File) sheetMap.get(entry));
            copyStream(is, zos);
            is.close();
        }
        zos.close();
        zip.close();
    }


    /**
     * 대용량 엑셀
     * @param in
     * @param out
     * @throws IOException
     */
    public void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] chunk = new byte[1024];
        int count;
        while ((count = in.read(chunk)) >=0 ) {
            out.write(chunk,0,count);
        }
    }


    /**
     * 대용량 엑셀 - 스타일 생성
     * 스타일MAP생성
     * @param wb
     * @return
     */
    public Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb){
        Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();

        XSSFCellStyle style5 = wb.createCellStyle();
        XSSFFont font5 = wb.createFont();
        font5.setFontHeightInPoints((short) 8); //폰트크기
        font5.setColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex()); //폰트색상
        font5.setFontName("맑은 고딕"); //폰트명
        font5.setBold(true);
        font5.setFontHeight(10);
        style5.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.PALE_BLUE.getIndex());  // 배경색
        style5.setFillForegroundColor(HSSFColor.HSSFColorPredefined.PALE_BLUE.getIndex());  // 배경색
        style5.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style5.setBorderTop(BorderStyle.valueOf(BORDER_MEDIUM));
        style5.setBorderLeft(BorderStyle.valueOf(BORDER_MEDIUM));
        style5.setBorderBottom(BorderStyle.valueOf(BORDER_MEDIUM));
        style5.setBorderRight(BorderStyle.valueOf(BORDER_MEDIUM));
        style5.setFont(font5);

        styles.put("header", style5);

        XSSFCellStyle style1 = wb.createCellStyle();
        XSSFFont font1 = wb.createFont();
        font1.setFontHeightInPoints((short) 8); //폰트크기
        font1.setFontName("맑은 고딕"); //폰트명
        style1.setBorderRight(BorderStyle.valueOf(BORDER_THIN));              //테두리 설정
        style1.setBorderLeft(BorderStyle.valueOf(BORDER_THIN));
        style1.setBorderTop(BorderStyle.valueOf(BORDER_THIN));
        style1.setBorderBottom(BorderStyle.valueOf(BORDER_THIN));
        style1.setFont(font1);
        styles.put("data", style1);

        return styles;
    }

    public Map getExcelParam(HttpServerRequest request) throws Exception {

        String ds_colNm = request.param("ds_colNm");
        String ds_colKey = request.param ("ds_colKey");
        String ds_colWidth = request.param("ds_colWidth");
        String ds_colType = request.param("ds_colType");

        String[] colNm = ds_colNm.split(",");
        String[] colKey = ds_colKey.split(",");
        String[] colWidth = ds_colWidth.split(",");
        String[] colType = ds_colType.split(",");

        List<String> colNameList = new ArrayList<String>();
        List<String> colKeyList = new ArrayList<String>();
        List<String> colWidthList = new ArrayList<String>();
        List<String> colTypeList = new ArrayList<String>();

        for(int i=0; i<colNm.length; i++){
            String Name = StringUtil.isNullToString(colNm[i]);
            String key = StringUtil.isNullToString(colKey[i]);
            String width = StringUtil.isNullToString(colWidth[i]);
            String type = StringUtil.isNullToString(colType[i]);
            colNameList.add(Name);
            colKeyList.add(key);
            colWidthList.add(width);
            colTypeList.add(type);
        }

        Map<String, List<String>> excelConf = new HashMap<String, List<String>>();
        excelConf.put("colNm", colNameList);
        excelConf.put("colKey", colKeyList);
        excelConf.put("colWidth", colWidthList);
        excelConf.put("colType", colTypeList);

        return excelConf;
    }

    public Map getExcelPostParam(Map parm) throws Exception {

        String ds_colNm = StringUtil.isNullToString(parm.get("colNm"));
        String ds_colKey = StringUtil.isNullToString(parm.get("colKey"));
        String ds_colWidth = StringUtil.isNullToString(parm.get("colWidth"));
        String ds_colType = StringUtil.isNullToString(parm.get("colType"));

        String[] colNm = ds_colNm.split(",");
        String[] colKey = ds_colKey.split(",");
        String[] colWidth = ds_colWidth.split(",");
        String[] colType = ds_colType.split(",");

        List<String> colNameList = new ArrayList<String>();
        List<String> colKeyList = new ArrayList<String>();
        List<String> colWidthList = new ArrayList<String>();
        List<String> colTypeList = new ArrayList<String>();

        for(int i=0; i<colNm.length; i++){
            String Name = StringUtil.isNullToString(colNm[i]);
            String key = StringUtil.isNullToString(colKey[i]);
            String width = StringUtil.isNullToString(colWidth[i]);
            String type = StringUtil.isNullToString(colType[i]);
            colNameList.add(Name);
            colKeyList.add(key);
            colWidthList.add(width);
            colTypeList.add(type);
        }

        Map<String, List<String>> excelConf = new HashMap<String, List<String>>();
        excelConf.put("colNm", colNameList);
        excelConf.put("colKey", colKeyList);
        excelConf.put("colWidth", colWidthList);
        excelConf.put("colType", colTypeList);

        return excelConf;
    }

    public Map<String,File> createExcelReady(XSSFWorkbook wb,String excelName) throws Exception {

        /**
         * 1. 엑셀 대상 디렉토리 및 템프 디렉토리 검사
         *    디렉토리 미 생성시 신규 생성.
         */

        excelDir = "";

        String targetSheetFilePath = excelDir; //  + "\\" + "BIGEXCLE";
        String templeteSheetFilePath = excelDir; //+ "\\" + "BIGEXCLE";

        File _targetdir = new File(targetSheetFilePath);
        File _tempdir = new File(templeteSheetFilePath);


        String dirStr = _targetdir+"\\"+excelName+".xlsx";
        String dirStr2 = _targetdir+"\\TEMP.xlsx";
        String dirStr3 = _targetdir+"\\"+excelName+".xml";

        File _targetFile = new File(dirStr);
        File _tempFile = new File(dirStr2);
        File _xml = new File(dirStr3);

        FileOutputStream _tempFileOutputStream = null;

        if(!_targetdir.isDirectory()) _targetdir.mkdir();
        if(!_tempdir.isDirectory()) _tempdir.mkdir();

        /**
         * 2. target 엑셀파일 생성.
         *    temp   XML파일 생성.
         */
        try {
            _targetFile.createNewFile();
            _tempFile.createNewFile();
//        	_targetFile = File.createTempFile(excelName, ".xlsx", _targetdir);
//        	_tempFile = File.createTempFile("TMP", ".xlsx", _tempdir);
            _tempFileOutputStream = new FileOutputStream(_tempFile);
            wb.write(_tempFileOutputStream);
            _xml.createNewFile();
//        	_xml = File.createTempFile(excelName, ".xml", _tempdir);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        } finally {
            if(_tempFileOutputStream != null)
                try {
                    _tempFileOutputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }

        Map<String,File> rtnMap = new HashMap<String,File>();
        rtnMap.put("_targetdir", _targetdir);
        rtnMap.put("_tempdir", _tempdir);
        rtnMap.put("_targetFile", _targetFile);
        rtnMap.put("_tempFile", _tempFile);
        rtnMap.put("_xml", _xml);

        return rtnMap;
    }

    public void fetchExcelData(String sheetRef,File _targetFile,File _xml,File _tempFile) throws Exception {

        FileOutputStream out = null;
        ZipOutputStream zos = null;

        try{
            out = new FileOutputStream(_targetFile);
            Map sheetMap = new HashMap();
            sheetMap.put(sheetRef.substring(1), _xml);
            this.substitute(_tempFile, sheetMap, out);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }finally{
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                if (zos != null)
                    zos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 엑셀 업로드
     * @param int - 엑셀을 읽기 시작하는 행
     * @param HttpServletRequest
     * @return List<HashMap<String, String>>
     */
    @SuppressWarnings("deprecation")
    public List<HashMap<String, String>> upload(int index, HttpServerRequest req) throws Exception{

        ArrayList<HashMap<String, String>> rtnList = new ArrayList<HashMap<String,String>>();
        String fullFilePath = null;
        try{

            MultipartHttpServletRequest multi = (MultipartHttpServletRequest) req;
            final Map<String, MultipartFile> files = multi.getFileMap();
            // 파일의 정보 취득
            if (!files.isEmpty()) {

                Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();
                while(itr.hasNext()){
                    Map.Entry<String, MultipartFile> entry = itr.next();
                    MultipartFile mFile = entry.getValue();

                    File saveFolder = new File(excelDir);  // 폴더 확인

                    // 폴더가 없을경우 폴더 생성
                    if(!saveFolder.exists() || saveFolder.isFile()){
                        saveFolder.mkdirs();
                    }

                    String fileName = mFile.getOriginalFilename();
                    fullFilePath    = excelDir + File.separator + fileName;


                    /*todo 파일명 새로 생성해줘야할듯? 중복안되게 다른2pc에서 같은파일명을업로드할경우 에러일껄..*/
                    /*화면에서 완벽히 못거를거같은데.. 확장자 체크도 해야됨...*/

                    // 파일 복사
                    mFile.transferTo(new File(fullFilePath));

                    if (fileName.indexOf(".xlsx") > -1 || fileName.indexOf(".XLSX") > -1){

                        XSSFWorkbook workBook  = new XSSFWorkbook(new FileInputStream(new File(fullFilePath)));
                        XSSFSheet sheet        = null;
                        XSSFRow row            = null;
                        XSSFCell cell          = null;

                        int sheetNum = workBook.getNumberOfSheets();

                        for(int i = 0; i < sheetNum; i++){

                            // 여러 시트를 사용하려나 여기서.. 한다면 주석풀고 테스트해봐야할듯?
                            if(i == 1){
                                break;
                            }

                            sheet    = workBook.getSheetAt(i);
                            //int rows = sheet.getPhysicalNumberOfRows();
                            for(int j = 0; true; j++){

                                if(j < 1 && sheet.getRow(j) == null){//null이면 공백
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put("cell0","");
                                    rtnList.add(map);
                                    continue;
                                }else if(sheet.getRow(j) == null){
                                    break;
                                }

                                row       = sheet.getRow(j);
                                int cells = row.getPhysicalNumberOfCells();
                                HashMap<String, String> map = new HashMap<String, String>();
                                for(short c = 0; c < cells; c++){
                                    cell = null;
                                    cell = row.getCell(c);
                                    try {

                                        switch(cell.getCellType()){
                                            case NUMERIC:
                                                if(HSSFDateUtil.isCellDateFormatted(cell)){
                                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                                    String data = formatter.format(cell.getDateCellValue());
                                                    map.put("cell" + c, data);
                                                }else{
                                                    DecimalFormat df = new DecimalFormat("0.##");	/* DecimalFormat "0" 소수점 무시, "0.##" 세번째 소수점 반올림 */
                                                    map.put("cell" + c, df.format(cell.getNumericCellValue())); /*넘버형 소숫점이나 쓸일 있으려나 ..*/
                                                }
                                                break;
                                            case STRING:
                                                map.put("cell" + c, cell.getStringCellValue());
                                                break;
                                            case FORMULA :
                                                map.put("cell" + c, cell.getCellFormula());
                                                break;
                                            default:
                                                map.put("cell" + c, "");
                                                break;
                                        }

                                    } catch (Exception e) {

                                        //에러나면 ""처리
                                        map.put("cell" + c, "");

                                    }

                                }

                                rtnList.add(map);

                            }

                        }

                    }else{
                        HSSFWorkbook workBook  = new HSSFWorkbook(new FileInputStream(new File(fullFilePath)));
                        HSSFSheet sheet        = null;
                        HSSFRow row            = null;
                        HSSFCell cell          = null;

                        int sheetNum = workBook.getNumberOfSheets();

                        for(int i = 0; i < sheetNum; i++){

                            // 여러 시트를 사용하려나 여기서.. 한다면 주석풀고 테스트해봐야할듯?
                            if(i == 1){
                                break;
                            }

                            sheet    = workBook.getSheetAt(i);
                            //int rows = sheet.getPhysicalNumberOfRows();
                            for(int j = 0; true; j++){

                                if(j < 9 && sheet.getRow(j) == null){//null이면 공백
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put("cell0","");
                                    rtnList.add(map);
                                    continue;
                                }else if(sheet.getRow(j) == null){
                                    break;
                                }

                                row       = sheet.getRow(j);
                                int cells = row.getPhysicalNumberOfCells();
                                HashMap<String, String> map = new HashMap<String, String>();

//    						 HSSFCell nullChk = row.getCell(0);
//    						 if(!(nullChk.getCellType() == 0 || nullChk.getCellType() == 1 || nullChk.getCellType() == nullChk.CELL_TYPE_FORMULA)){
//    							 break;
//    						 }

                                for(short c = 0; c < cells; c++){
                                    cell = null;
                                    cell = row.getCell(c);

                                    switch(cell.getCellType()){

                                        case NUMERIC:
                                            if(HSSFDateUtil.isCellDateFormatted(cell)){
                                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                                String data = formatter.format(cell.getDateCellValue());
                                                map.put("cell" + c, data);
                                            }else{
                                                DecimalFormat df = new DecimalFormat("0.##");	/* DecimalFormat "0" 소수점 무시, "0.##" 세번째 소수점 반올림 */
                                                map.put("cell" + c, df.format(cell.getNumericCellValue())); /*넘버형 소숫점이나 쓸일 있으려나 ..*/
                                            }
                                            break;
                                        case STRING:
                                            map.put("cell" + c, cell.getStringCellValue());
                                            break;
                                        case FORMULA :
                                            map.put("cell" + c, cell.getCellFormula());
                                            break;
                                        default:
                                            map.put("cell" + c, "");
                                            break;
                                    }

                                }

                                rtnList.add(map);

                            }

                        }
                    }

                }
            }

        }catch(Exception e){
            throw new Exception("엑셀파일을 읽는중 오류가 발생하였습니다.");
        }finally{

            // 파일이 있을경우 삭제
            File file = new File(fullFilePath);
            if(file.isFile()){
                file.delete();
            }

        }

        return rtnList;

    }

    /**
     * 엑셀업로드 - 우선순위 조회 후 컬럼 CONCAT
     * @param List
     * @param String
     * @return String
     * @throws Exception
     */
    public static String colConcat(List list, String param) throws Exception {
        String rtn = "";
        int colNum = 1;

        Iterator iter = list.iterator();
        while(iter.hasNext()){
            Map map = (Map) iter.next();

            String udf1 = StringUtil.isNullToString(map.get("udf1"));

            if(udf1.equals(param)){
                if(colNum < 10){
                    rtn = "c00" + colNum;
                }else if(9 < colNum && colNum < 100){
                    rtn = "c0" + colNum;
                }else{
                    rtn = "c" + colNum;
                }

                break;
            }

            colNum++;

        }

        return rtn;
    }

    /**
     * 엑셀업로드 - 컬럼사이즈 판단
     * @param List
     * @param String
     * @return String
     * @throws Exception
     */
    public static String colSize(List list, String param) throws Exception {
        String rtn = "";

        Iterator iter = list.iterator();
        while(iter.hasNext()){
            Map map = (Map) iter.next();

            String udf1 = StringUtil.isNullToString(map.get("udf1"));

            if(udf1.equals(param)){

                return StringUtil.isNullToString(map.get("udf2"));

            }

        }

        return rtn;
    }

}
