package com.mlschool.schoolmannagement.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mlschool.schoolmannagement.model.Eleve;
import com.mlschool.schoolmannagement.model.ecole.Ecole;
import com.mlschool.schoolmannagement.model.modelResult.Bilan;
import com.mlschool.schoolmannagement.model.modelResult.BilanGeneral;
import com.mlschool.schoolmannagement.model.modelResult.ResultListNoteByType;

@Service
public class BulletinService {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
    private static SimpleDateFormat simpleDateFormatYear = new SimpleDateFormat("yyyy") ;

    @Autowired
    private EcoleService ecoleService;

    @Autowired
    private AbscenceEleveService abscenceEleveService;

    @Autowired
    private EleveService eleveService;

    @Autowired
    private EvaluationService evaluationService;

    private static Image image;
    private final DecimalFormat df = new DecimalFormat("##0.00", DecimalFormatSymbols.getInstance(Locale.US));
    private static PdfPCell cell;
    private static Paragraph paragraph;
    private static Paragraph paragraph1;
    private static Paragraph paragraph2;
    private static Paragraph paragraph3;
    private static PdfPTable table1;
    private static PdfPTable table2;
    private String mat;
    private boolean open;
    
    public String Imprimer(String periode, Integer id) throws DocumentException, IOException{

        Ecole ecole = ecoleService.findAll(Sort.by(Sort.Direction.DESC, "id")).get(0);
    
        Eleve eleve = eleveService.findById(id);
        
        Long nombre = evaluationService.getNombreEleveByClasse(eleve.getIdclasse());

        List<ResultListNoteByType> litteraires = evaluationService.getListNoteEleveByTypeMatiereAndPeriode( id, periode,2) ;
        Bilan bilanLitteraire = evaluationService.traitementBilan( id, periode,2) ;
        
        List<ResultListNoteByType> scientifiques = evaluationService.getListNoteEleveByTypeMatiereAndPeriode( id, periode,1) ;
        Bilan bilanScientifique = evaluationService.traitementBilan( id, periode,1) ;
       
        List<ResultListNoteByType> autres = evaluationService.getListNoteEleveByTypeMatiereAndPeriode( id, periode,3) ;
        Bilan bilanAutre = evaluationService.traitementBilan( id, periode,3) ;
        
        BilanGeneral bilanGeneral = evaluationService.getBilanGeneral(id,periode);


        String lastMoyenne = evaluationService.getLastMoyenneByClasse(eleve.getIdclasse(),periode);
        String firstMoyenne = evaluationService.getFirstMoyenneByClasse(eleve.getIdclasse(),periode);
        String classMoyenne = evaluationService.getMoyenneByClasse(eleve.getIdclasse(),periode);
        
        
        Integer abscences = abscenceEleveService.getSumAbscenceEleve(id);
        Integer abscenceJustifier = abscenceEleveService.getSumAbscenceEleveJustifier(id);
        Integer abscenceNonJustifier = abscenceEleveService.getSumAbscenceEleveNonJustifier(id);


        Document doc = new Document();
        File folder = new File("C:/Users/foueg/OneDrive/Documents/bulletins");
        if(!folder.exists())
            folder.mkdir();
        PdfWriter.getInstance(doc, new FileOutputStream("C:/Users/foueg/OneDrive/Documents/bulletins/Bulletin--" + eleve.getNom() + ".pdf"));
        doc.open();

        String nom = "",mail = "",adresse = "",ville = "",contact = "";
        String year = "";
        
        year = simpleDateFormatYear.format(ecole.getAnnee());
        nom = ecole.getNom();
        mail = ecole.getEmail();
        adresse = ecole.getAdresse();
        ville = ecole.getVille();
        contact = ecole.getContact();
        image = Image.getInstance("public/images/" + ecole.getLogo()); 

        PdfPTable headerTable = new PdfPTable(13);
        Font hearderfont = new Font(Font.FontFamily.TIMES_ROMAN,7,Font.BOLD,BaseColor.BLACK);
        Font fontb = new Font(Font.FontFamily.TIMES_ROMAN,7,Font.BOLD,BaseColor.BLACK);
        Font fontr = new Font(Font.FontFamily.TIMES_ROMAN,7,Font.BOLD,BaseColor.RED);
        Font fontB = new Font(Font.FontFamily.TIMES_ROMAN,9,Font.BOLD,BaseColor.BLACK);
        Font fontBR = new Font(Font.FontFamily.TIMES_ROMAN,9,Font.BOLD,BaseColor.RED);
        Font fontR = new Font(Font.FontFamily.TIMES_ROMAN,9,Font.BOLD,BaseColor.RED);
        Font fontb2 = new Font(Font.FontFamily.TIMES_ROMAN,9,0,BaseColor.BLACK);
        headerTable.setWidthPercentage((float)100.0);
        paragraph = new Paragraph();
        paragraph1 = new Paragraph(("Ministere de l'education nationnal\n\n"),hearderfont);
        paragraph.add(paragraph1);
        paragraph2 = new Paragraph(("de l'enseignement technique et de la formation\n\n"),hearderfont);
        paragraph.add(paragraph2);
        paragraph3 = new Paragraph(("professionnel"),hearderfont);
        paragraph.add(paragraph3);
        cell = new PdfPCell(paragraph);
        cell.setBackgroundColor(new BaseColor(255,240,193));
        cell.setColspan(4);
        cell.setRowspan(3);
        headerTable.addCell(cell);
        
        cell = new PdfPCell();
        
        // image = new ImageIcon(logo).getImage().getScaledInstance(15, 15, 0);
        image.scaleAbsolute(30,30);
        cell.setBackgroundColor(new BaseColor(255,240,193));
        cell.addElement(image);
        cell.setRowspan(3);
        headerTable.addCell(cell);
        
        paragraph = new Paragraph();
        paragraph1 = new Paragraph(("BULLETIN DE NOTES\n\n"),new Font(Font.FontFamily.TIMES_ROMAN,10,Font.BOLD,BaseColor.BLACK));
        paragraph.add(paragraph1);
        paragraph2 = new Paragraph((" "+periode+" \n\n"),new Font(Font.FontFamily.TIMES_ROMAN,10,Font.BOLD,BaseColor.RED));
        paragraph.add(paragraph2);
        paragraph3 = new Paragraph(("Annee academique : "+year+" - "+(year+1)+" "),new Font(Font.FontFamily.TIMES_ROMAN,10,Font.BOLD,BaseColor.BLACK));
        paragraph.add(paragraph3);
        cell = new PdfPCell(paragraph);
        cell.setBackgroundColor(new BaseColor(255,240,193));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(4);
        cell.setRowspan(3);
        headerTable.addCell(cell);

        cell = new PdfPCell(new Phrase(("Republique du cameroon\n\nPaix travail patrie\n"),hearderfont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(4);
        cell.setRowspan(3);
        cell.setBackgroundColor(new BaseColor(255,240,193));
        headerTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase(("etablissement: "+nom+" \n\nadresse e-mail: "+mail+" "),hearderfont));
        cell.setColspan(4);
        cell.setRowspan(2);
        cell.setBackgroundColor(new BaseColor(255,240,193));
        headerTable.addCell(cell);
        cell = new PdfPCell(new Phrase((" Ville: "+ville+" \n\nadresse postal: "+adresse+" "),hearderfont));
        cell.setColspan(5);
        cell.setRowspan(2);
        cell.setBackgroundColor(new BaseColor(255,240,193));
        headerTable.addCell(cell);

        paragraph = new Paragraph(("Contact : "+contact+""),hearderfont);
        cell = new PdfPCell(paragraph);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(4);
        cell.setRowspan(2);
        cell.setBackgroundColor(new BaseColor(255,240,193));
        headerTable.addCell(cell);
        doc.add(headerTable);
        doc.add(new Paragraph(" "));

        //-----------------------------------------------------------deuxiememe blog----------------------------------------------------

        Long effectif;
        String name = "",prenom = "",classe = "",sexe = "",DN = "",LN = "";

        name = eleve.getNom();
        prenom = eleve.getPrenom();
        classe = eleve.getClasse().getClasse();
        sexe = eleve.getSexe();
        DN = simpleDateFormat.format(eleve.getDateNaissance());
        LN = eleve.getLieuNaissance();
        image = Image.getInstance("public/images/" + eleve.getPhoto());
        effectif = nombre;


        table1 = new PdfPTable(26);
        table1.setWidthPercentage((float)100.0);
        paragraph = new Paragraph();
        paragraph.add(new Chunk(name.toUpperCase()+"  "+prenom.toUpperCase()+"\n\n",new Font(Font.FontFamily.TIMES_ROMAN,8,Font.BOLD,BaseColor.RED)));
        paragraph.add(new Chunk("Matricule : ",fontb));
        paragraph.add(new Chunk(eleve.getMatricule()+"\n\n",fontr));
        paragraph.add(new Chunk("Classe : ",fontb));
        paragraph.add(new Chunk(classe+"\n\n",fontr));
        paragraph.add(new Chunk("Effectif : ",fontb));
        paragraph.add(new Chunk(effectif+"",fontr));
        cell = new PdfPCell(paragraph);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(9);
        cell.setRowspan(4);
        table1.addCell(cell);

        paragraph = new Paragraph();
        paragraph.add(new Chunk("Sexe : ",fontb));
        paragraph.add(new Chunk(sexe+"\n\n",fontr));
        paragraph.add(new Chunk("Date de naissance : ",fontb));
        paragraph.add(new Chunk(DN+"\n\n",fontr));
        paragraph.add(new Chunk("Lieu de naissance : ",fontb));
        paragraph.add(new Chunk(LN+"\n\n",fontr));
        paragraph.add(new Chunk("Statut : ",fontb));
        paragraph.add(new Chunk("Affecte",fontr));
        cell = new PdfPCell(paragraph);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(10);
        cell.setRowspan(4);
        table1.addCell(cell);

        cell = new PdfPCell();
        
        image.scaleAbsolute(50,50);
        cell.addElement(image);
        cell.setColspan(7);
        cell.setRowspan(4);
        table1.addCell(cell);
        doc.add(table1);

        //new table -------------------------------------------------------------------------------------------------------------
        doc.add(new Paragraph("    "));
        table2 = new PdfPTable(7);
        table2.setWidths(new float[] {3.5f,1f,1f,1.2f,1f,1.5f,2.5f});
        table2.setWidthPercentage((float)100.0);
        table2.setHeaderRows(1);

        cell = new PdfPCell(new Phrase("Matiere",fontB));
        cell.setBackgroundColor(new BaseColor(27,172,231));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setFixedHeight(20);
        table2.addCell(cell);

        cell = new PdfPCell(new Phrase("Moy/20",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(27,172,231));
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("Coef",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(27,172,231));
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("Moy*Coef",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(27,172,231));
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("Rang",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(27,172,231));
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("Appreciation",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(27,172,231));
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("Professeur",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(27,172,231));
        table2.addCell(cell);

        //-------------------------billan litteraire
        Double moyLitt = 0.0,coefLitt = 0.0;

            for(ResultListNoteByType litteraire : litteraires){
                Integer d1 = litteraire.getEvaluation().getAffectation().getCoefficient();
                Double d2 = litteraire.getEvaluation().getNote() * litteraire.getEvaluation().getAffectation().getCoefficient();
                moyLitt += d2;
                coefLitt += d1;
              
                cell = new PdfPCell(new Phrase(litteraire.getEvaluation().getAffectation().getMatiere().getNom(),fontb2));
                cell.setFixedHeight(17);
                table2.addCell(cell);
                cell = new PdfPCell(new Phrase(litteraire.getEvaluation().getNote().toString(),fontb2));
                cell.setBackgroundColor(new BaseColor(255,231,176));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table2.addCell(cell);
                cell = new PdfPCell(new Phrase(String.valueOf(d1),fontb2));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table2.addCell(cell);
                cell = new PdfPCell(new Phrase(String.valueOf(d2),fontb2));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(new BaseColor(255,231,176));
                table2.addCell(cell);
                cell =new PdfPCell(new Phrase(litteraire.getRang(),fontb2));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table2.addCell(cell);
                cell = new PdfPCell(new Phrase(litteraire.getAppreciation(),fontb2));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table2.addCell(cell);
                cell = new PdfPCell(new Phrase(litteraire.getEvaluation().getAffectation().getProfesseur().getNom(),fontb2));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table2.addCell(cell);
            }


        cell = new PdfPCell(new Phrase("BILLANT LITTERAIRE",fontB));
        cell.setFixedHeight(17);
        table2.addCell(cell);
        String v = df.format(moyLitt/coefLitt);
        cell = new PdfPCell(new Phrase(v+"",fontB));
        cell.setBackgroundColor(new BaseColor(255,231,176));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase(coefLitt+"",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase(moyLitt+"",fontB));
        cell.setBackgroundColor(new BaseColor(255,231,176));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell =new PdfPCell(new Phrase("",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);


        Double moyLitt1 = 0.0,coefLitt1 = 0.0;
              
                for(ResultListNoteByType scientifique : scientifiques){
                    Integer d1 = scientifique.getEvaluation().getAffectation().getCoefficient();
                    Double d2 = scientifique.getEvaluation().getNote() * scientifique.getEvaluation().getAffectation().getCoefficient();
                    moyLitt1 += d2;
                    coefLitt1 += d1;
                  
                    cell = new PdfPCell(new Phrase(scientifique.getEvaluation().getAffectation().getMatiere().getNom(),fontb2));
                    cell.setFixedHeight(17);
                    table2.addCell(cell);
                    cell = new PdfPCell(new Phrase(scientifique.getEvaluation().getNote().toString(),fontb2));
                    cell.setBackgroundColor(new BaseColor(255,231,176));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table2.addCell(cell);
                    cell = new PdfPCell(new Phrase(String.valueOf(d1),fontb2));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table2.addCell(cell);
                    cell = new PdfPCell(new Phrase(String.valueOf(d2),fontb2));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(new BaseColor(255,231,176));
                    table2.addCell(cell);
                    cell =new PdfPCell(new Phrase(scientifique.getRang(),fontb2));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table2.addCell(cell);
                    cell = new PdfPCell(new Phrase(scientifique.getAppreciation(),fontb2));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table2.addCell(cell);
                    cell = new PdfPCell(new Phrase(scientifique.getEvaluation().getAffectation().getProfesseur().getNom(),fontb2));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table2.addCell(cell);
                }


        //------------------------------------------billant scientifique
        cell = new PdfPCell(new Phrase("BILLANT SCIENTIFIQUE",fontB));
        cell.setFixedHeight(17);
        table2.addCell(cell);
        String v2 = df.format(moyLitt1/coefLitt1);
        cell = new PdfPCell(new Phrase(v2,fontB));
        cell.setBackgroundColor(new BaseColor(255,231,176));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase(coefLitt1+"",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase(moyLitt1+"",fontB));
        cell.setBackgroundColor(new BaseColor(255,231,176));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell =new PdfPCell(new Phrase("",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);

       
        Double moyLitt2 = 0.0,coefLitt2 = 0.0;
        for(ResultListNoteByType autre : autres){
            Integer d1 = autre.getEvaluation().getAffectation().getCoefficient();
            Double d2 = autre.getEvaluation().getNote() * autre.getEvaluation().getAffectation().getCoefficient();
            moyLitt2 += d2;
            coefLitt2 += d1;
          
            cell = new PdfPCell(new Phrase(autre.getEvaluation().getAffectation().getMatiere().getNom(),fontb2));
            cell.setFixedHeight(17);
            table2.addCell(cell);
            cell = new PdfPCell(new Phrase(autre.getEvaluation().getNote().toString(),fontb2));
            cell.setBackgroundColor(new BaseColor(255,231,176));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(d1),fontb2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(d2),fontb2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(new BaseColor(255,231,176));
            table2.addCell(cell);
            cell =new PdfPCell(new Phrase(autre.getRang(),fontb2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.addCell(cell);
            cell = new PdfPCell(new Phrase(autre.getAppreciation(),fontb2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.addCell(cell);
            cell = new PdfPCell(new Phrase(autre.getEvaluation().getAffectation().getProfesseur().getNom(),fontb2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.addCell(cell);
        }


        //------------------------------------------------------bilan autre
        cell = new PdfPCell(new Phrase("BILLANT AUTRE",fontB));
        cell.setFixedHeight(17);
        table2.addCell(cell);
        String val =df.format(moyLitt2/coefLitt2);
        cell = new PdfPCell(new Phrase(val+"",fontB));
        cell.setBackgroundColor(new BaseColor(255,231,176));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase(coefLitt2+"",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase(moyLitt2+"",fontB));
        cell.setBackgroundColor(new BaseColor(255,231,176));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell =new PdfPCell(new Phrase("",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);

        //----------------------------------------BILLANT GRNERAL-------------------------------------------------
		//------------------------------------------------------------------BILLANT GENERAL----------------------------------------------
        String valeur = bilanGeneral.getMoyenne();
        String valeur1 = df.format(bilanGeneral.getTotalNote());
        cell = new PdfPCell(new Phrase("BILLANT GENERALE",fontBR));
        cell.setFixedHeight(17);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase(valeur+"",fontBR));
        cell.setBackgroundColor(new BaseColor(255,231,176));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase((bilanGeneral.getTotalCoef())+"",fontBR));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase(valeur1+"",fontBR));
        cell.setBackgroundColor(new BaseColor(255,231,176));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell =new PdfPCell(new Phrase(bilanGeneral.getRang()+"",fontBR));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("",fontBR));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("",fontBR));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        
        doc.add(table2);
        
        //nouvelle table ----------------------------------------------------------------------
        doc.add(new Paragraph("    "));
        table2 = new PdfPTable(6);
        table2.setWidths(new float[] {1f,1f,1f,1f,1f,1f});
        table2.setWidthPercentage((float)100.0);
        table2.setHeaderRows(1);
        cell = new PdfPCell(new Phrase("Resultat de la \nclasse",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setFixedHeight(20);
        cell.setBackgroundColor(BaseColor.WHITE);
        table2.addCell(cell);

        cell = new PdfPCell(new Phrase("Assuidite",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.WHITE);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("Resultat de l'eleve",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.WHITE);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("Mentions Conseil de \nClasse",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.WHITE);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("Appreciation du Conseil de \nClasse",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.WHITE);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("Cachet du chef de \nl'etablissement",fontB));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.WHITE);
        table2.addCell(cell);
        
        paragraph = new Paragraph();
        paragraph.add(new Chunk("Moyenne\nMax : ",fontb));
        paragraph.add(new Chunk(firstMoyenne+"\n\n\n",fontr));
        paragraph.add(new Chunk("Moyenne\nMin : ",fontb));
        paragraph.add(new Chunk(lastMoyenne+"\n\n\n",fontr));

        paragraph.add(new Chunk((classMoyenne)+"\n",fontr));
        cell = new PdfPCell(paragraph);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table2.addCell(cell);

        paragraph = new Paragraph();
        paragraph.add(new Chunk("Total d'heure\nd'abscence : ",fontb));
        paragraph.add(new Chunk(abscences+"\n\n\n",fontr));
        paragraph.add(new Chunk("Total\nJustifier : ",fontb));
        paragraph.add(new Chunk(abscenceJustifier+"\n\n\n",fontr));
        paragraph.add(new Chunk("Total\nNon justifier : ",fontb));
        paragraph.add(new Chunk(abscenceNonJustifier+"\n",fontr));
        cell = new PdfPCell(paragraph);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table2.addCell(cell);
    

        paragraph = new Paragraph();
        paragraph.add(new Chunk("Moyenne\n : ",fontb));
        paragraph.add(new Chunk(bilanGeneral.getMoyenne()+"\n\n\n\n\n",fontr));
        paragraph.add(new Chunk("Rang\n: ",fontb));
        paragraph.add(new Chunk(bilanGeneral.getRang()+"\n\n\n",fontr));
        cell = new PdfPCell(paragraph);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table2.addCell(cell);

        paragraph = new Paragraph();
        paragraph.add(new Chunk("\n\n\nSanctions\n  ",fontB));
        cell = new PdfPCell(paragraph);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table2.addCell(cell);

        paragraph = new Paragraph();
        paragraph.add(new Chunk("\n\n\nSignature du \nProviseur\n  ",fontB));
        cell = new PdfPCell(paragraph);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table2.addCell(cell);

        cell = new PdfPCell(new Phrase("",fontR));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(cell);
        doc.add(table2);

        doc.close();

        // Desktop.getDesktop().open(new File("C:/Users/foueg/OneDrive/Documents/bulletins/Bulletin--" + eleve.getNom() + ".pdf"));

        return "C:/Users/foueg/OneDrive/Documents/bulletins/Bulletin--" + eleve.getNom() + ".pdf";
    }
}
