package com.skilldistillery.history;

import java.io.File;
import java.time.LocalDate;

public abstract class SETTINGS {
	
	public static final int MIN_YEAR = 1000;
	public static final int MAX_YEAR = 2030;
	public static final int MAX_TERM_NUMBER = 1000;
	public static final int CURRENT_YEAR = LocalDate.now().getYear();
	public static final String FILE_PATH = "resources";
	public static final String RESTRICTED_FILENAME = "presidents.tsv";
	public static final String LOAD_DIR = FILE_PATH + File.separator;
	public static final String FILE_HEADER = "#	First	Middle	Last	Inagurated	Left office	Elections won	Reason left office	Party\n";
	
}
