package com.systemscanner.api.service.impl;

import com.systemscanner.api.model.entity.ScannerInstance;
import com.systemscanner.api.model.mongo.software.Software;
import com.systemscanner.api.model.projection.DisplayUser;
import com.systemscanner.api.repository.jpa.UserRepository;
import com.systemscanner.api.service.MailNotificationSender;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.joining;

@Service
@RequiredArgsConstructor
public class MailNotificationSenderImpl implements MailNotificationSender {

	private final MailSender mailSender;
	private final UserRepository userRepository;

	@Override
	public void sendSoftwareNotificationMessage(ScannerInstance scannerInstance, Set<Software> forbiddenSoftware) {
		Function<String, SimpleMailMessage> newMessageTo = email -> {
			val message = new SimpleMailMessage();
			message.setTo(email);

			return message;
		};

		UnaryOperator<SimpleMailMessage> sendContent = message -> {
			message.setBcc();

			return message;
		};

		this.userRepository.findAllByScannerInstancePid(scannerInstance.getPid())
				.stream()
				.map(DisplayUser::getEmail)
				.map(newMessageTo)
				.forEach(this.mailSender::send);
	}

	private String buildForbiddenSoftwareMessage(ScannerInstance scannerInstance, Set<Software> forbiddenSoftware) {
		return forbiddenSoftware
				.stream()
				.map(Software::getName)
				.collect(collectingAndThen(joining(", "),
						software ->
								String.format("Your instance %s contains forbidden software: %s", scannerInstance.getName(), software)
				));
	}

}
