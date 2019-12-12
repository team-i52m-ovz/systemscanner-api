package com.systemscanner.api.repository.jpa;

import com.systemscanner.api.model.entity.SoftwarePublisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Set;

public interface SoftwarePublisherRepository extends JpaRepository<SoftwarePublisher, Long> {

	Set<SoftwarePublisher> findAllByRiskFactorGreaterThan(BigDecimal riskFactor);

}
