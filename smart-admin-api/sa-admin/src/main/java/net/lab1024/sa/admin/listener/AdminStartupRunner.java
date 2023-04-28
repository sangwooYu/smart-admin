package net.lab1024.sa.admin.listener;

import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.common.common.code.ErrorCodeRegister;
import net.lab1024.sa.common.config.ScheduleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * admin 应用启动加载
 *
 * @Author 1024创新实验室-主任:卓大
 * @Date 2021-08-26 18:46:32
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Slf4j
@Component
public class AdminStartupRunner implements CommandLineRunner {

    @Autowired
    private ScheduleConfig scheduleConfig;

    @Override
    public void run(String... args) {

        // 초기화 상태 코드
        int codeCount = ErrorCodeRegister.initialize();

        //TODO <卓大> ：시간 제한 작업을 설정할지 여부는 실제 상황에 따라 다릅니다.
        String destroySchedules = "봄 시간 지정 작업 @Schedule이 시작되었습니다.";
//        destroySchedules = scheduleConfig.destroy();

        log.info("\n --------------- [1024 이노베이션 랩 웜 팁:] 오류코드 총 초기화 완료: {}개! ---------------" +
                 "\n --------------- [1024 이노베이션 랩 웜 팁:] {}---------------\n", codeCount, destroySchedules);

    }
}