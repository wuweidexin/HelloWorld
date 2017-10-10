package com.chen.javase.jawin;

import org.jawin.DispatchPtr;
import org.jawin.win32.Ole32;

public class CreateWord {
	public static void main(String[] args) {
		try {
			Ole32.CoInitialize();
			DispatchPtr app = new DispatchPtr("Word.Application");
			app.put("Visible", true);
			Ole32.CoUninitialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
