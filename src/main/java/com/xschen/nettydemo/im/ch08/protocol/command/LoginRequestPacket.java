package com.xschen.nettydemo.im.ch08.protocol.command;

import lombok.Data;

import static com.xschen.nettydemo.im.ch08.protocol.command.Command.LOGIN_REQUEST;

/**
 * @author xschen
 */

@Data
public class LoginRequestPacket extends Packet {

    private Integer userId;

    private String username;

    private String password;


    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
