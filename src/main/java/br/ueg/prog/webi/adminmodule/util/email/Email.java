/*
 * Email.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.prog.webi.adminmodule.util.email;

import br.ueg.prog.webi.api.util.CollectionUtil;
import br.ueg.prog.webi.api.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import javax.activation.DataHandler;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.*;

/**
 * Class that encapsulates the sending email implementation.
 * 
 * @author UEG
 */
public class Email {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("email-config");
	private static final String USER_NAME = RESOURCE_BUNDLE.getString("mail.user");
	private static final String PASSWORD = RESOURCE_BUNDLE.getString("mail.password");
	private static final String HOST_NAME = RESOURCE_BUNDLE.getString("mail.host");
	private static final String SMTP_PORT = RESOURCE_BUNDLE.getString("mail.smtp-port");
	private static final String DEFAULT_REMITTER_ADDRESS = RESOURCE_BUNDLE.getString("mail.default-remitter-address");
	private static final String DEFAULT_CHARSET = RESOURCE_BUNDLE.getString("mail.default-charset");

	private String remitterAddress;
	private Set<String> addressesTO;
	private Set<String> addressesCC;
	private Set<String> addressesBCC;
	private Map<String, byte[]> attachments;
	private Map<String, byte[]> attachmentsImage;

	private String charset;
	private String subject;
	private String body;

	/**
	 * Constructor classe.
	 */
	public Email() {
		this.attachments = new HashMap<String, byte[]>();
		this.attachmentsImage = new HashMap<String, byte[]>();
		this.addressesTO = new LinkedHashSet<>();
		this.addressesCC = new LinkedHashSet<String>();
		this.addressesBCC = new LinkedHashSet<String>();
	}

	/**
	 * Add addressTO.
	 *
	 * @param addressTO -
	 * @return -
	 */
	public Email addAddressTO(final String addressTO) {
		if (!StringUtils.isBlank(addressTO)) {
			this.addressesTO.add(addressTO.toLowerCase().trim());
		}
		return this;
	}

	/**
	 * Add all addressesTO.
	 *
	 * @param addressesTO -
	 * @return -
	 */
	public Email addAllAddressTO(final List<String> addressesTO) {
		if (!CollectionUtil.isEmpty(addressesTO)) {
			this.addressesTO.addAll(addressesTO);
		}
		return this;
	}

	/**
	 * @return the addressesTO
	 */
	public Set<String> getAddressesTO() {
		return addressesTO;
	}

	/**
	 * Add addressCC.
	 *
	 * @param addressCC -
	 * @return -
	 */
	public Email addAddressCC(final String addressCC) {
		if (!StringUtils.isBlank(addressCC)) {
			this.addressesCC.add(addressCC.toLowerCase().trim());
		}
		return this;
	}

	/**
	 * Add all addressesCC.
	 *
	 * @param addressesCC -
	 * @return -
	 */
	public Email addAllAddressCC(final List<String> addressesCC) {
		if (!CollectionUtil.isEmpty(addressesTO)) {
			this.addressesCC.addAll(addressesCC);
		}
		return this;
	}

	/**
	 * @return the addressesCC
	 */
	public Set<String> getAddressesCC() {
		return this.addressesCC;
	}

	/**
	 * Add addressBCC.
	 *
	 * @param addressBCC -
	 * @return -
	 */
	public Email addAddressBCC(final String addressBCC) {
		if (!StringUtils.isBlank(addressBCC)) {
			this.addressesBCC.add(addressBCC.toLowerCase().trim());
		}
		return this;
	}

	/**
	 * Add all addressesBCC.
	 *
	 * @param addressesBCC -
	 * @return -
	 */
	public Email addAllAddressBCC(final List<String> addressesBCC) {
		if (!CollectionUtil.isEmpty(addressesBCC)) {
			this.addressesBCC.addAll(addressesBCC);
		}
		return this;
	}

	/**
	 * @return the addressesBCC
	 */
	public Set<String> getAddressesBCC() {
		return addressesBCC;
	}

	/**
	 * Add attachments image.
	 *
	 * @param cid -
	 * @return -
	 */
	public Email addAttachmentImage(final String cid, byte[] data) {
		if (Strings.isNotEmpty(cid) && data != null) {
			attachmentsImage.put(cid, data);
		}
		return this;
	}

	/**
	 * Add attachments.
	 *
	 * @param name -
	 * @return -
	 */
	public Email addAttachment(final String name, byte[] data) {
		if (Strings.isNotEmpty(name) && data != null) {
			attachments.put(name, data);
		}
		return this;
	}

	/**
	 * @param charset the charset to set
	 * @return -
	 */
	public Email setCharset(String charset) {
		this.charset = charset;
		return this;
	}

	/**
	 * @return the charset
	 */
	public String getCharset() {
		return Strings.isEmpty(charset) ? DEFAULT_CHARSET : charset;
	}

	/**
	 * @return the remitterAddress
	 */
	public String getRemitterAddress() {
		return Strings.isEmpty(remitterAddress) ? DEFAULT_REMITTER_ADDRESS : remitterAddress;
	}

	/**
	 * @param remitterAddress the remitterAddress to set
	 * @return -
	 */
	public Email setRemitterAddress(String remitterAddress) {
		this.remitterAddress = remitterAddress;
		return this;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 * @return -
	 */
	public Email setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	/**
	 * @return the body -
	 */
	public String getBody() {
		return Strings.isEmpty(body) ? " " : body;
	}

	/**
	 * @param body the body to set -
	 * @return -
	 */
	public Email setBody(String body) {
		this.body = body;
		return this;
	}

	/**
	 * Realiza a validação dos atributos associados ao e-mail.
	 *
	 * @throws EmailException -
	 */
	private void validateParams() throws EmailException {
		List<String> invalidAddresses = new ArrayList<>();

		if (addressesTO.isEmpty() && addressesCC.isEmpty()) {
			throw new EmailException("Email must have at least one recipient.");
		}

		/* Charset */
		if (this.charset != null) {
			try {
				Charset.forName(charset);
			} catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
				throw new EmailException("Charset invalid (" + getCharset() + ")");
			}
		}

		/* TO */
		for (String address : getAddressesTO()) {
			if (!Util.isValidEmail(address)) {
				invalidAddresses.add(address);
			}
		}

		/* CC */
		for (String address : addressesCC) {
			if (!Util.isValidEmail(address)) {
				invalidAddresses.add(address);
			}
		}

		/* BCC */
		for (String address : addressesBCC) {
			if (!Util.isValidEmail(address)) {
				invalidAddresses.add(address);
			}
		}

		if (!CollectionUtil.isEmpty(invalidAddresses)) {
			throw new EmailException("Invalid Recipients: " + CollectionUtil.getCollectionAsString(invalidAddresses)
					+ ". Please add valid email addresses.");
		}
	}

	/**
	 * Sends the email according to the given parameters.
	 *
	 * @return -
	 * @throws EmailException -
	 */
	public void send() throws EmailException {
		try {
			validateParams();

			String charset = getCharset();
			Session session = getSession();

			Multipart content = new MimeMultipart();

			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(body, "text/html; charset=" + charset);
			content.addBodyPart(bodyPart);

			for (String attachmentsName : attachments.keySet()) {
				byte[] data = attachments.get(attachmentsName);

				MimeBodyPart attachmentPart = getAttachmentPart(attachmentsName, data);
				content.addBodyPart(attachmentPart);
			}

			for (String cid : attachmentsImage.keySet()) {
				byte[] data = attachmentsImage.get(cid);

				MimeBodyPart attachmentPart = getAttachmentImagePart(cid, data);
				content.addBodyPart(attachmentPart);
			}

			Message msg = new MimeMessage(session);
			msg.setSentDate(new Date());
			msg.setContent(content);

			String subject = MimeUtility.encodeText(this.subject, charset, null);
			msg.setSubject(subject);

			String remitterAddress = getRemitterAddress();
			msg.setFrom(new InternetAddress(remitterAddress));

			InternetAddress[] addressesTO = getInternetAdresses(this.addressesTO);
			msg.setRecipients(Message.RecipientType.TO, addressesTO);

			if (!CollectionUtil.isEmpty(addressesCC)) {
				InternetAddress[] addressesCC = getInternetAdresses(this.addressesCC);
				msg.setRecipients(Message.RecipientType.CC, addressesCC);
			}

			if (!CollectionUtil.isEmpty(addressesBCC)) {
				InternetAddress[] addressesBCC = getInternetAdresses(this.addressesBCC);
				msg.setRecipients(Message.RecipientType.BCC, addressesBCC);
			}

			Transport.send(msg);
		} catch (Exception e) {
			throw new EmailException(e.getMessage(), e);
		}
	}

	/**
	 * Retorna a instância de {@link MimeBodyPart} conforme os parâmetros
	 * informados.
	 * 
	 * @param name  -
	 * @param data -
	 * @return -
	 * @throws MessagingException -
	 */
	private MimeBodyPart getAttachmentPart(String name, byte[] data) throws MessagingException {
		MimetypesFileTypeMap mimeTypes = new MimetypesFileTypeMap();
		ByteArrayDataSource dataSource = new ByteArrayDataSource(data, mimeTypes.getContentType(name));

		MimeBodyPart part = new MimeBodyPart();
		part.setDataHandler(new DataHandler(dataSource));
		part.setFileName(name);
		return part;
	}

	/**
	 * Returns an instance of {@link MimeBodyPart} according to the given
	 * parameters.
	 * 
	 * @param cid  -
	 * @param data -
	 * @return -
	 * @throws MessagingException -
	 */
	private MimeBodyPart getAttachmentImagePart(String cid, byte[] data) throws MessagingException {
		MimetypesFileTypeMap mimeTypes = new MimetypesFileTypeMap();
		ByteArrayDataSource dataSource = new ByteArrayDataSource(data, mimeTypes.getContentType(cid));

		MimeBodyPart part = new MimeBodyPart();
		part.setDataHandler(new DataHandler(dataSource));
		part.setHeader("Content-ID", "<" + cid.trim() + ">");
		return part;
	}

	/**
	 * Returns the instance of {@link Session} as per the email configuration
	 * parameters.
	 * 
	 * @return -
	 */
	private Session getSession() {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", HOST_NAME);
		properties.put("mail.smtp.port", Integer.parseInt(SMTP_PORT));
		properties.put("mail.smtp.auth", Boolean.TRUE.toString());
		properties.put("mail.smtp.starttls.enable", Boolean.TRUE.toString());

		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USER_NAME, PASSWORD);
			}
		};
		return Session.getInstance(properties, auth);
	}

	/**
	 * Returns the array of {@link InternetAddress} according to the list of
	 * recipients.
	 * 
	 * @param recipients -
	 * @return -
	 * @throws AddressException -
	 */
	private InternetAddress[] getInternetAdresses(final Set<String> recipients) throws AddressException {
		int index = 0;
		InternetAddress[] addresses = new InternetAddress[recipients.size()];

		for (String endereco : recipients) {
			addresses[index++] = new InternetAddress(endereco);
		}
		return addresses;
	}

}
