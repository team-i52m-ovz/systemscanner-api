package com.systemscanner.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.systemscanner.api.model.mongo.Report;
import com.systemscanner.api.repository.mongo.ReportMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/temp")
@RequiredArgsConstructor
public class TempController {

	private final ReportMongoRepository reportMongoRepository;


	@GetMapping
	public Set<Report> findAllBySearch(@RequestParam(value = "search", required = false) String search) {
		return Optional.ofNullable(search)
				.map(this.reportMongoRepository::findAllByHardwareLike)
				.orElseGet(this.reportMongoRepository::findAllDistinct);

	}


	@PostMapping
	public Report saveNew() throws IOException {
		return this.reportMongoRepository.insert(Report.builder().hardware("SSD Drive").hardwareInfo(new ObjectMapper().readerFor(Document.class).readValue(MOCK)).build());
	}

	private static final String MOCK = "{  \n" +
			"   \"Hardware\":{  \n" +
			"      \"Win32_BIOS\":[  \n" +
			"         [  \n" +
			"            {  \n" +
			"               \"BuildNumber\":\"\",\n" +
			"               \"CodeSet\":\"\",\n" +
			"               \"CurrentLanguage\":\"enUS\",\n" +
			"               \"Description\":\"Q78 Ver. 01.03.00\",\n" +
			"               \"EmbeddedControllerMajorVersion\":\"4\",\n" +
			"               \"EmbeddedControllerMinorVersion\":\"83\",\n" +
			"               \"InstallDate\":\"\",\n" +
			"               \"LanguageEdition\":\"\",\n" +
			"               \"Manufacturer\":\"HP\",\n" +
			"               \"Name\":\"Q78 Ver. 01.03.00\",\n" +
			"               \"PrimaryBIOS\":\"true\",\n" +
			"               \"SerialNumber\":\"5CG8397N6G\",\n" +
			"               \"SoftwareElementID\":\"Q78 Ver. 01.03.00\",\n" +
			"               \"SystemBiosMajorVersion\":\"3\",\n" +
			"               \"SystemBiosMinorVersion\":\"0\",\n" +
			"               \"TargetOperatingSystem\":\"0\",\n" +
			"               \"Version\":\"HPQOEM - 0\"\n" +
			"            }\n" +
			"         ]\n" +
			"      ],\n" +
			"      \"Win32_BaseBoard\":[  \n" +
			"         [  \n" +
			"            {  \n" +
			"               \"Depth\":\"\",\n" +
			"               \"Description\":\"Base Board\",\n" +
			"               \"Height\":\"\",\n" +
			"               \"HostingBoard\":\"true\",\n" +
			"               \"InstallDate\":\"\",\n" +
			"               \"Manufacturer\":\"HP\",\n" +
			"               \"Model\":\"\",\n" +
			"               \"Name\":\"Base Board\",\n" +
			"               \"PartNumber\":\"\",\n" +
			"               \"PoweredOn\":\"true\",\n" +
			"               \"Product\":\"83B2\",\n" +
			"               \"Removable\":\"false\",\n" +
			"               \"Replaceable\":\"false\",\n" +
			"               \"RequirementsDescription\":\"\",\n" +
			"               \"RequiresDaughterBoard\":\"false\",\n" +
			"               \"SerialNumber\":\"PHDHQ00WBBD01Y\",\n" +
			"               \"SlotLayout\":\"\",\n" +
			"               \"SpecialRequirements\":\"\",\n" +
			"               \"Status\":\"OK\",\n" +
			"               \"Weight\":\"\",\n" +
			"               \"Width\":\"\"\n" +
			"            }\n" +
			"         ]\n" +
			"      ]\n" +
			"   }\n" +
			"}";
}
