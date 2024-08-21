package com.example.mywatch1.actuator.endpoint;

import com.example.mywatch1.actuator.dto.LibraryInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Endpoint(id = "myLibraryInfo")  // endpoint id 지정. 필수! -> controller 의 request mapping 정도의 역할
public class MyLibraryInfoEndpoint {


    @ReadOperation    // read (읽기)요청에 대한 메서드라는 의미 ->  controller 의  @GetMapping 과 유사
    public List<LibraryInfoDTO> getLibraryInfos(
            // queryString 을 통해 받기 
            @Nullable String name,   // org.springframework.lang.Nullable 인거 주의 (jakarta 가 아니라)
            boolean includeVersion) { // includeVersion 은 @Nullable 이 없으므로 필수적으로 querystring 을 줘야된다.

        // 라이브러리 정보를 읽어서 name, version을 가져오는 코드가 있어야 하나 하드코딩으로 대체함.
        LibraryInfoDTO libraryInfo1 = new LibraryInfoDTO();
        libraryInfo1.setName("logback");
        libraryInfo1.setVersion("1.0.0");

        LibraryInfoDTO libraryInfo2 = new LibraryInfoDTO();
        libraryInfo2.setName("jackson");
        libraryInfo2.setVersion("2.0.0");

        List<LibraryInfoDTO> resultList = Arrays.asList(libraryInfo1, libraryInfo2);

        if (name != null) {
            resultList = resultList.stream()
                    .filter(libraryInfo -> {
                        return libraryInfo.getName().equals(name);
                    })
                    .toList();
        }
        if (includeVersion == false) {
            resultList = resultList.stream()
                    .map(libraryInfo -> {
                        LibraryInfoDTO simpleInfo = new LibraryInfoDTO();
                        simpleInfo.setName(libraryInfo.getName());
                        // version 정보는 포함하지 않음.
                        return simpleInfo;
                    }).toList();
        }

        return resultList;
    }


    @WriteOperation // write 요청(생성 및 수정)에 대한 메서드라는 의미 ->  controller 의  @PostMapping 과 유사
                        // 참고로 Actuator에는 @PatchMapping 처럼 수정에 대해 세분화되어있진 않다
    public String changeSomething(
            // body로 데이터 받기
                // 단순한 파라미터 타입만 지원되기에 DTO와 같이 여러 멤버변수를 가진 객체를 파라미터로 지정해주는건 지원되지 않음 주의
                //그러니까 body 로 데이터를 받더라도,  Actuator 엔드포인트로 받는 경우는 DTO 파라미터로 여러 값을 한꺼번에 받을 수 없다
            String name, boolean enableSomething) {
        String response = "name: "+name+", enableSomething: "+enableSomething;
        log.info(response);
        return response;
    }


    @ReadOperation
    public String getAPathVariable(
            //pathvariable 딱 하나 받기
            @Selector String path1
    ) {
        log.info("path1: {}", path1);
        return path1;
    }


/*
    @ReadOperation
    public String getMultiPathVariable(
            //pathvariable 여러개 받기
            // match >> Selector 어노테이션의 필드로, SINGLE 혹은 ALL_REMAINING 을 값으로 줄 수 있음
                // SINGLE : 디폴트로, 정확히 1개를 받음
                // ALL_REMAINING : 여러개 받음

            @Selector(match = Selector.Match.ALL_REMAINING) String[] path
    ) {
        log.info("path: {}", Arrays.asList(path));
        return Arrays.asList(path).toString();
    }

 */




}