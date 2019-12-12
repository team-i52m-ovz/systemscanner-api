package com.systemscanner.api.service;

import com.systemscanner.api.model.entity.ScannerInstance;
import com.systemscanner.api.model.mongo.software.Software;

import java.util.Set;

public interface MailNotificationSender {

	void sendSoftwareNotificationMessage(ScannerInstance scannerInstance, Set<Software> forbiddenSoftware);

}
