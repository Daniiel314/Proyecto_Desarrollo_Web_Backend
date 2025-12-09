package com.customer.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.api.entity.Region;

@RestController
@RequestMapping("/region")
public class CtrlRegion {

	@GetMapping
	public List<Region> getRegions(){
		
		List<Region> regions = new ArrayList<>();

		regions.add(new Region(1, "América del Norte", "NA", 1));
		regions.add(new Region(2, "América Latina y el Caribe", "LATAM", 1));
		regions.add(new Region(3, "Europa Occidental", "WEU", 0));
		regions.add(new Region(4, "Europa del Este", "EEU", 1));
		regions.add(new Region(5, "África del Norte", "NAF", 0));
		regions.add(new Region(6, "África Subsahariana", "SSA", 1));
		regions.add(new Region(7, "Medio Oriente", "ME", 1));
		regions.add(new Region(8, "Asia Oriental", "EAS", 0));
		regions.add(new Region(9, "Asia Meridional", "SAS", 0));
		regions.add(new Region(10, "Oceanía", "OCE", 1));
		
		return regions;
	}
	
	@PostMapping
	public String createRegion(@RequestBody Region region) {
		
		// código necesario para crear una región

		System.out.println(region.getRegion_id());
		System.out.println(region.getRegion());
		System.out.println(region.getTag());
		System.out.println(region.getStatus());
		
		return "La región se ha registrado exitosamente";
	}
	
	
	
	

}
