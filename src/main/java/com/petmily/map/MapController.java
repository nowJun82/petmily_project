package com.petmily.map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/map")
@Controller
public class MapController {

    @GetMapping("/hospital")
    public String showHospital() {
        return "map_hospital";
    }

    @GetMapping("/hotel")
    public String showHotel() {
        return "map_hotel";
    }

    @GetMapping("/accommodations")
    public String showAccommodations() {
        return "map_accommodations";
    }
}