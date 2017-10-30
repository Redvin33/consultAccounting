package com.booking.consultAccounting.cfg;

import com.booking.consultAccounting.service.BookingService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Lauri on 30.10.2017.
 */
@Configuration
public class TestConfig {
    @Bean
    public BookingService bookingService() {
        return Mockito.mock(BookingService.class);
    }


}
