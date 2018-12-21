package com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack;






public  class ChatStateExtension implements PacketExtension{
	private ChatState state;
	public ChatStateExtension(ChatState state) {
        this.state = state;
    }
	@Override
	public String getElementName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CharSequence toXML() {
		// TODO Auto-generated method stub
		return null;
	}
}
