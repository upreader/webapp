package com.upreader.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Handles Messages in a Context
 * 
 * @author Flavius
 *
 */
public class Messages {
	private static final String SO_MESSAGE_QUEUE = "_message_queue";
	private static final List<Message> EMPTY_MESSAGES = Collections.unmodifiableList(new ArrayList(0));
	private final SessionNamedValues session;

	public Messages(Context context) {
		this.session = context.session();
	}

	public Messages put(Message message) {
		if (message != null) {
			List<Message> messages = this.session.getObject(SO_MESSAGE_QUEUE);
			if (messages == null) {
				messages = new LinkedList<>();
				this.session.putObject(SO_MESSAGE_QUEUE, messages);
			}
			messages.add(message);
		}

		return this;
	}

	public Messages normal(String message) {
		return put(message, MessageType.NORMAL);
	}

	public Messages warning(String message) {
		return put(message, MessageType.WARNING);
	}

	public Messages success(String message) {
		return put(message, MessageType.SUCCESS);
	}

	public Messages error(String message) {
		return put(message, MessageType.ERROR);
	}

	public Messages put(String message, MessageType type) {
		if (message != null) {
			Message m;
			if (type == null) {
				m = new Message(message);
			} else {
				m = new Message(message, type);
			}

			put(m);
		}

		return this;
	}

	public List<Message> queue() {
		return this.session.getObject(SO_MESSAGE_QUEUE);
	}

	public List<Message> list() {
		List<Message> queue = queue();
		if (queue != null) {
			List<Message> messages = new ArrayList<>(queue);
			queue.clear();
			return messages;
		}

		return EMPTY_MESSAGES;
	}

	public List<Message> listFiltered(MessageType[] type) {
		List<MessageType> types = Arrays.asList(type);
		List<Message> messages = new ArrayList<>();
		List<Message> queue = queue();
		if (queue != null) {
			ListIterator<Message> iterator = queue.listIterator();
			while (iterator.hasNext()) {
				Message m = iterator.next();
				if (types.contains(m.getType())) {
					iterator.remove();
					messages.add(m);
				}
			}
		}
		return messages;
	}

	public List<Message> listErrors() {
		return listFiltered(new MessageType[] { MessageType.ERROR });
	}

	public List<Message> listNormal() {
		return listFiltered(new MessageType[] { MessageType.NORMAL });
	}

	public List<Message> listWarning() {
		return listFiltered(new MessageType[] { MessageType.WARNING });
	}

	public List<Message> listSuccess() {
		return listFiltered(new MessageType[] { MessageType.SUCCESS });
	}

}
