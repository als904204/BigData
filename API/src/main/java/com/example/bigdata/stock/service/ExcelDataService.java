package com.example.bigdata.stock.service;

import com.example.bigdata.stock.dto.UploadFileDto.UploadRes;
import com.example.bigdata.stock.entity.Stock;
import com.example.bigdata.stock.repository.StockRepository;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelDataService {
    private static final int DATA_CELL = 0;
    private static final int OPEN_CELL = 1;
    private static final int HIGH_CELL = 2;
    private static final int LOW_CELL = 3;
    private static final int CLOSE_CELL = 4;
    private static final int VOLUME_CELL = 5;
    private static final int CHANGE_CELL = 6;
    private static final int PREDICTED_CELL = 7;
    private static final int CODE_CELL = 8;
    private static final int OPINION_CELL = 9;

    private final StockRepository stockRepository;
    @Transactional
    public UploadRes readAndSaveExcelData(MultipartFile file) {

        String companyName = extractCompanyName(file.getOriginalFilename());
        LocalDate lastDateInDB = null;

        boolean isAlreadySaved = stockRepository.existsByCompany(companyName);

        // 데이터가 이미 존재한다면, 업데이트
        // lastDateInDB 변수에 이미 저장된 주식의 가장 최근 날짜 저장
        if (isAlreadySaved) {
            log.info(companyName,"{} : already saved company so we'll update");
            // 이미 있는 데이터의 마지막 날짜를 가져옴
            lastDateInDB = stockRepository.findLastDateByCompany(companyName).orElseThrow(()-> new RuntimeException(
                "no data for last"));
        }


        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<Stock> stocksToSaveOrUpdate = new ArrayList<>();

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // 첫 번째 행(컬럼 속성)은 건너뜀
                if (currentRow.getRowNum() == 0) {
                    continue;
                }

                LocalDate date = currentRow.getCell(DATA_CELL).getLocalDateTimeCellValue()
                    .toLocalDate();

                // 해당 회사의 주식 데이터가 이미 존재 (조건문이 참이면 없는 데이터만 저장 하면 됨)
                // && 현재 처리 중인 행의 날짜(date)가 lastDateInDB 이후인 날짜가 아닐 때
                // (즉, 이 행의 날짜가 데이터베이스의 최근 날짜보다 이전이거나 같은 날짜면 if 문 실행 X)
                if(lastDateInDB !=null && !date.isAfter(lastDateInDB)){
                    continue;
                }
                Stock stock = createStockFromRow(companyName, currentRow);
                stocksToSaveOrUpdate.add(stock);
            }

            stockRepository.saveAll(stocksToSaveOrUpdate);
            return new UploadRes(companyName);
            
        }catch (IOException e) {
            throw new RuntimeException("Error reading an Excel file", e);
        }
    }

    // 엑셀 행 데이터 객체화
    private Stock createStockFromRow(String companyName, Row currentRow) {
        Stock stock = Stock.builder()
            .company(companyName)
            .date(currentRow.getCell(DATA_CELL).getLocalDateTimeCellValue().toLocalDate())
            .open((int) currentRow.getCell(OPEN_CELL).getNumericCellValue())
            .high((int) currentRow.getCell(HIGH_CELL).getNumericCellValue())
            .low((int) currentRow.getCell(LOW_CELL).getNumericCellValue())
            .close((int) currentRow.getCell(CLOSE_CELL).getNumericCellValue())
            .volume((int) currentRow.getCell(VOLUME_CELL).getNumericCellValue())
            .changes(BigDecimal.valueOf(currentRow.getCell(CHANGE_CELL).getNumericCellValue()))
            .build();
        // 예측 가격이 있는 경우 설정
        Cell predictedCell = currentRow.getCell(PREDICTED_CELL);
        if (predictedCell != null && predictedCell.getCellType() == CellType.NUMERIC) {
            stock.setPredicted(predictedCell.getNumericCellValue());
        }

        // 주식 코드가 있는 경우 설정
        Cell codeCell = currentRow.getCell(CODE_CELL);
        if (codeCell != null) {
            if (codeCell.getCellType() == CellType.NUMERIC) {
                stock.setCode(String.valueOf((int) codeCell.getNumericCellValue()));
                System.out.println("stock.getCode() = " + stock.getCode());
            } else if (codeCell.getCellType() == CellType.STRING) {
                stock.setCode(codeCell.getStringCellValue());
            }
        }

        // 이동 평균선 분석 의견
        Cell opinionCell = currentRow.getCell(OPINION_CELL);
        if (opinionCell != null && opinionCell.getCellType() == CellType.STRING) {
            stock.setOpinion(opinionCell.getStringCellValue());
        }

        return stock;
    }

    // 엑셀 파일에서 이름 추출
    private String extractCompanyName(String filename) {

        if (filename == null || !filename.contains("_")) {
            log.error("Invalid file name format. Expected format 'companyName_' OR Not null");
            throw new RuntimeException(
                "Invalid file name format. Expected format 'companyName_' OR Not null");
        }
        int underscoreIndex = filename.indexOf("_");
        if (underscoreIndex != -1) {
            return filename.substring(0, underscoreIndex);
        }
        return filename;
    }


}
