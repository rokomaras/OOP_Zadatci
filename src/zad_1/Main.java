package SOLUTIONS.src.zad_1;

import java.io.IOException;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) throws IOException {

        Specialist sAI1 = new Specialist("SPEC_1", "AISpecialist");
        Specialist sAI2 = new Specialist("SPEC_4", "AISpecialist");

        Specialist sDL1 = new Specialist("SPEC_2", "DLSpecialist");
        Specialist sDL2 = new Specialist("SPEC_3", "DLSpecialist");


        Specialist sNLP1 = new Specialist("SPEC_5", "NLPSpecialist");
        Specialist sNLP2 = new Specialist("SPEC_7", "NLPSpecialist");


        LAB_STAFF lab = new LAB_STAFF("AI Research Lab");


        lab.addSpecialistToLab(sAI1);
        lab.addSpecialistToLab(sAI2);
        lab.addSpecialistToLab(sDL1);
        lab.addSpecialistToLab(sDL2);
        lab.addSpecialistToLab(sNLP1);
        lab.addSpecialistToLab(sNLP2);


        System.out.println("=== Početni izlist članova (LAB.toString) ===");
        System.out.println(lab);

        System.out.println("=== Početni izlist članova (AUX_CLS.listDataFromTreeMap) ===");
        AUX_CLS.listDataFromTreeMap(lab.getStaffMap());


        boolean addedDuplicate = lab.addSpecialistToLab(sAI1);
        System.out.println("Pokušaj ponovnog dodavanja postojećeg člana (očekivano false): " + addedDuplicate);


        AUX_CLS.saveTreeMapDataToBinFile(lab.getStaffMap());
        System.out.println("Podaci spremljeni u binarnu datoteku.");


        TreeMap<String, TreeSet<Specialist>> loaded = AUX_CLS.loadTreeMapDataFromBinFile();
        int newlyAdded = lab.addAllFromTreeMap(loaded);
        System.out.println("Nakon učitavanja i dodavanja iz bin datoteke, novo dodano: " + newlyAdded);


        System.out.println("=== Završni izlist članova (LAB.toString) ===");
        System.out.println(lab);
    }
}
