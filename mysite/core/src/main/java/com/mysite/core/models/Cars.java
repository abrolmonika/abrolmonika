package com.mysite.core.models;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.DamConstants;

/** 
 * This model reads JSON data from a file and returns that data as string.
 * 
 * @author monika.abrol
 *
 */

@Model(adaptables = SlingHttpServletRequest.class, 
	   defaultInjectionStrategy = DefaultInjectionStrategy.REQUIRED)
public class Cars {
	private static final Logger LOG = LoggerFactory.getLogger(Cars.class);
	private static final String SLASH = "/";
	private static final String ORIGINAL = "original";
	
	@SlingObject
	private ResourceResolver resourceResolver;
	
	@ValueMapValue
	private String jsonFilePath;
	
	private InputStream carsJsonInputStream;
	
	@PostConstruct
	private void initialize() {
			LOG.info("Inside initialize");
		    
			String jsonFileOriginalRenditionPath = jsonFilePath + SLASH + JcrConstants.JCR_CONTENT + SLASH + 
					DamConstants.RENDITIONS_FOLDER + SLASH + ORIGINAL + SLASH + JcrConstants.JCR_CONTENT;
			
			LOG.info("Json Data is stored in {}'s jcr:data property", jsonFileOriginalRenditionPath);
			if(resourceResolver != null) {
				Resource jsonDataResource = resourceResolver.getResource(jsonFileOriginalRenditionPath);
				if (jsonDataResource != null) {
					ValueMap vauleMap = jsonDataResource.adaptTo(ValueMap.class);
					this.carsJsonInputStream = vauleMap.get(JcrConstants.JCR_DATA, InputStream.class);
				} else {
					LOG.info("Json File {} Resource is null !", jsonFileOriginalRenditionPath);
				}
			} else {
				LOG.info("Resource Resolver is null !");
			}
			LOG.info("Exiting initialize");
	}
    
	/**
	 * Returns the cars object from the JSON file as a String
	 * 
	 * @return the cars
	 */
	public String getCars() {
		
		String dropdownOptions = "";
		try {
			dropdownOptions =  IOUtils.toString(this.carsJsonInputStream, StandardCharsets.UTF_8.name());
			LOG.info("Json File contains :: {}", dropdownOptions);
		} catch (IOException e) {
			LOG.error("IOException has occured while reading inputStream to String ::", e);
		}
		
		return dropdownOptions;
	}
}
