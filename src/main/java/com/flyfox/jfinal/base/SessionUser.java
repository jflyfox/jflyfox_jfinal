package com.flyfox.jfinal.base;

import com.jfinal.plugin.activerecord.Model;

public class SessionUser<M extends Model<M>> extends BaseModel<M> {

	private static final long serialVersionUID = 1L;

	public Integer getUserID() {
		return getInt("userid") == null ? -1 : getInt("userid");
	}

}
