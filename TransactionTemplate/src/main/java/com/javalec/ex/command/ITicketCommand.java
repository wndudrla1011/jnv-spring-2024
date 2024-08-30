package com.javalec.ex.command;

import com.javalec.ex.dto.TicketDto;

public interface ITicketCommand {

	public void execute(TicketDto ticketDto);

}
