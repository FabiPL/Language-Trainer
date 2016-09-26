import java.io.*;
import java.util.*;

public class TrainerFct {

	private HashMap<String,ArrayList<String>> database;

	public TrainerFct() {
		database = new HashMap<>();
	}

	public HashMap<String, ArrayList<String>> getDatabase() {
		return database;
	}

    public void setDatabase(HashMap<String, ArrayList<String>> database) {
        this.database = database;
    }

    /**
	 * Loads WordDatabase file from LWTexport folder and adds everything in a Hashmap
	 * HashMap(word) with ArrayList(0translation,1sentence,2romanization,3taglist,4status,5language,6id
	 * @throws IOException
	 */
	public void load() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("LWTexport/WordDatabase_ExportedFromLWT.txt"), "UTF-8"));

		String line = null;
		String[] col = null;
		
		while((line = br.readLine()) != null) {
			col = line.split("\t");
			ArrayList<String> meta = new ArrayList<String>();
			
			meta.add(col[1]); // translation
			meta.add(col[2]); // sentence
			meta.add(col[3]); // romanization
			meta.add(col[4]); // status
			meta.add(col[5]); // language
			if(col.length > 6 && col[6] != null) { //id
			meta.add(col[6]); // id
			} else {
				meta.add("");
			}
			if(col.length > 7 && col[7] != null) { //tag list
				meta.add(col[7]);
			} else {
				meta.add("");
			}
			
			database.put(col[0], meta);
		}
		br.close();
        System.out.println("Database loaded...");
	}
	
	/**
	 * Saves a list to the WordDataBase file in the correct LWT export format
	 * @throws IOException
	 */
	public void save() throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("LWTexport/WordDatabase_ExportedFromLWT.txt"),"UTF-8"));
		
		for(Map.Entry<String, ArrayList<String>> set : database.entrySet()) {
			String output = set.getKey()+listToString(database.get(set.getKey()));
			
			bw.write(output);
			bw.newLine();
		}
		bw.close();
        System.out.println("Database updated...");
	}
	
	/**
	 * Change the translation of a word
	 * @param word - of which the translation needs to be changed
	 * @param newTranslation - * for empty (only allowed for statuses 98 and 99)
	 */
	public void changeTranslation(String word, String newTranslation) {
		database.get(word).set(0, newTranslation);
	}

    /**
     * Getter
     * @param term
     * @return translation of term
     */
	public String getTranslation(String term) {
        return database.get(term).get(0);
    }
	
	/**
	 * Change the example sentence of a word
	 * @param word - of which the sentence needs to be changed
	 * @param newSentence - String should contain word in {} (example: Особенно он {любит} мёд.)
	 */
	public void changeSentence(String word, String newSentence) {
		database.get(word).set(1, newSentence);
	}

    /**
     * Getter
     * @param term
     * @return sentence of term
     */
    public String getSentence(String term) {
        return database.get(term).get(1);
    }

    /**
	 * Change the romanization of a word
	 * @param word - of which the romanization needs to be changed
	 * @param newRomanization - new romanized version of a word (Особенно - asobena)
	 */
	public void changeRomanization(String word, String newRomanization) {
		database.get(word).set(2, newRomanization);
	}

    /**
     * Getter
     * @param term
     * @return romanization of term
     */
    public String getRomanization(String term) {
        return database.get(term).get(2);
    }

	/**
	 * Change the status of a word
	 * @param word - of which the status needs to be changed
	 * @param newStatus - level 1-5 | 98 for ignore and 99 for WellKnown
	 */
	public int changeStatus(String word, int newStatus) {
		if(newStatus < 1) {
            database.get(word).set(3, Integer.toString(1));
		} else if(newStatus > 5) {
            database.get(word).set(3, Integer.toString(5));
		} else {
		    database.get(word).set(3, Integer.toString(newStatus));
		}
		return Integer.parseInt(database.get(word).get(3));
	}

    /**
     * Getter
     * @param term
     * @return status of term (int!)
     */
    public int getStatus(String term) {
        return Integer.parseInt(database.get(term).get(3));
    }

    /**
     * Change the taglist of a word
     * @param word - of which the taglist needs to be changed
     * @param newTagList - new name for a tag list (example: adjective, adverb, noun)
     */
    public void changeTagList(String word, String newTagList) {
        database.get(word).set(6, newTagList);
    }

    /**
     * Getter
     * @param term
     * @return romanization of term
     */
    public String getTagList(String term) {
        return database.get(term).get(6);
    }
	
	/**
	 * Add a word unit
	 * @param newWord
	 * @param translation
	 * @param sentence
	 * @param romanization
	 * @param taglist
	 * @param status
	 */
	public void add(String newWord, String translation, String sentence, String romanization, String taglist, String status) {
		ArrayList<String> al = new ArrayList<>();

		al.add(translation);
		al.add(sentence);
		al.add(romanization);
		al.add(status); //verify status is a number!!!
        al.add(""); //language
        al.add(""); //internal id
        al.add(taglist);
		
		database.put(newWord, al);
	}
	
	/**
	 * Exports everything to the LWTimport folder (Creates a textfile for each status 
	 * -> can be imported via LWT - Import Term (in this version each still needs to be imported manually)
	 * @throws IOException
	 */
	public void createImportFiles() throws IOException {
		File dir = new File("LWTimport");
		File[] directoryListing = dir.listFiles();
		
		if (directoryListing != null) {
			for (File child : directoryListing) {
				child.delete(); //deletes already existent files before writing new ones
			}
		} else {
			// Directory not existent
		}

        HashMap<String,ArrayList<String>> temp = new HashMap<>();
        temp.putAll(database);

		BufferedWriter bw = null;
		
		for(Map.Entry<String, ArrayList<String>> set : temp.entrySet()) {
			int status = Integer.parseInt(set.getValue().get(3));
			String filename = "LWTimport/WordDatabase_Status";

            set.getValue().add(3, set.getValue().get(6));

			String output = set.getKey()+listToString(temp.get(set.getKey()));
			
			switch (status) {
				case 1:		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename+status+".txt",true),"UTF-8"));
							bw.write(output);
							bw.newLine();
							bw.flush();
							break;
				case 2:		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename+status+".txt",true),"UTF-8"));
							bw.write(output);
							bw.newLine();
							bw.flush();
							break;
				case 3:		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename+status+".txt",true),"UTF-8"));
							bw.write(output);
							bw.newLine();
							bw.flush();
							break;
				case 4:		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename+status+".txt",true),"UTF-8"));
							bw.write(output);
							bw.newLine();
							bw.flush();
							break;
				case 5:		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename+status+".txt",true),"UTF-8"));
							bw.write(output);
							bw.newLine();
							bw.flush();
							break;
				case 98:	bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename+status+".txt",true),"UTF-8"));
							bw.write(output);
							bw.newLine();
							bw.flush();
							break;
				case 99:	bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename+status+".txt",true),"UTF-8"));
							bw.write(output);
							bw.newLine();
							bw.flush();
							break;
				default: 	bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("LWTimport/Corrupt-Status.txt",true),"UTF-8"));
							bw.write(output);
							bw.newLine();
							bw.flush();
							break;
			}
		}
		bw.close();
        System.out.println("Import files for LWT successfully written...");
	}
	
	public static String listToString(List<?> list) {
	    String result = "";
	    for (int i = 0; i < list.size(); i++) {
	        result += "\t" + list.get(i);
	    }
	    return result;
	}

    /**
     * Getter for Random Term
     * @return term, doesn't return items with status '98', '99' and translations '*', empty ''
     */
    public String randomTermFromDatabase() {
        String randomKey = "";
        Boolean bool = true;

        while(true) {
            List<String> keys = new ArrayList<>(database.keySet());
            Random random = new Random();
            randomKey = keys.get(random.nextInt(keys.size()));
            if ((Integer.parseInt(database.get(randomKey).get(3)) != 98)                && (Integer.parseInt(database.get(randomKey).get(3)) != 99)
                 && !(database.get(randomKey).get(0).replace("\\s+", "").equals("*"))   && !(database.get(randomKey).get(0).replace("\\s+", "").equals(""))   ) {
                bool = false;
                return randomKey;
            } else {
                bool = true;
            }
        }
    }
}
