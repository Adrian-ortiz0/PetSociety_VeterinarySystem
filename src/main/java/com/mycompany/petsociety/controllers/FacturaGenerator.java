package com.mycompany.petsociety.controllers;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.mycompany.petsociety.models.MaterialMedico;
import com.mycompany.petsociety.models.Medicamento;
import com.mycompany.petsociety.models.TipoServicioAdicional;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FacturaGenerator {

    private static final String VETERINARIA_NOMBRE = "Clínica Veterinaria PetSociety";
    private static final String VETERINARIA_NIT = "NIT: 900.025.1818-7";
    private static final String VETERINARIA_DIRECCION = "Cra 20 # 110-69, Bucaramanga";
    private static final String VETERINARIA_TELEFONO = "Tel: (+57) 317-310-9599";
    private static final String FOTO_CANINO = "https://static.wikia.nocookie.net/discovery-kids/images/6/62/Iconcliff2005.png/revision/latest?cb=20220215181205&path-prefix=es";
    private static final String FOTO_FELINO = "https://static.wikia.nocookie.net/doblaje/images/9/9f/GarfieldCharacter.jpg/revision/latest?cb=20220926013827&path-prefix=es";
    private static final String FOTO_ROEDOR = "https://static.wikia.nocookie.net/disney/images/7/70/Remy.png/revision/latest/thumbnail/width/360/height/360?cb=20130307114718&path-prefix=es";
    private static final String FOTO_AVE = "https://i.pinimg.com/originals/75/57/f2/7557f21f0a6f4a4831ea2b5f94632a6d.png";
    private static final String CUFE = "636363636363636-J";
    private static final String QR = "/resources/qr.png";
    
    public static void generarFacturaPDFCompras(String nombreCliente, String cedulaCliente, BigDecimal costoTotal, ArrayList<Medicamento> medicamentosComprados) {
    File directorio = new File("facturas");
    if (!directorio.exists()) {
        directorio.mkdirs();
    }

    String nombreArchivo = String.format("facturas/factura_%s_%s_Compra.pdf",
            LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE),
            nombreCliente.replaceAll("\\s+", "_"));
    try {
        PdfWriter writer = new PdfWriter(nombreArchivo);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf)) {
            document.setMargins(30, 30, 30, 30);

            PdfFont fontBold = PdfFontFactory.createFont("Helvetica-Bold");
            PdfFont fontNormal = PdfFontFactory.createFont("Helvetica");
            document.add(new Paragraph(VETERINARIA_NOMBRE)
                    .setFont(fontBold)
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.DARK_GRAY));
            document.add(new Paragraph(VETERINARIA_NIT)
                    .setFont(fontNormal)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph(VETERINARIA_DIRECCION)
                    .setFont(fontNormal)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph(VETERINARIA_TELEFONO)
                    .setFont(fontNormal)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            document.add(new Paragraph("Datos del Cliente:").setFont(fontBold).setFontSize(12));
            Table customerTable = new Table(UnitValue.createPercentArray(3)).useAllAvailableWidth();
            customerTable.addCell(crearCelda("Nombre del Cliente:", fontBold));
            customerTable.addCell(crearCelda(nombreCliente, fontNormal));
            customerTable.addCell(crearCelda("Identificación:", fontBold));
            customerTable.addCell(crearCelda(cedulaCliente, fontNormal));
            customerTable.addCell(crearCelda("CUFE:", fontBold));
            customerTable.addCell(crearCelda(CUFE, fontNormal));
            document.add(customerTable);

            if (medicamentosComprados != null && !medicamentosComprados.isEmpty()) {
                document.add(new Paragraph("\nMedicamentos Comprados:").setFont(fontBold).setFontSize(12));
                Table medicationsTable = new Table(UnitValue.createPercentArray(new float[]{70, 30})).useAllAvailableWidth();
                medicationsTable.addHeaderCell(crearCeldaEncabezado("Medicamento", fontBold));
                medicationsTable.addHeaderCell(crearCeldaEncabezado("Precio", fontBold));
                for (Medicamento medicamento : medicamentosComprados) {
                    medicationsTable.addCell(crearCelda(medicamento.getNombre(), fontNormal));
                    medicationsTable.addCell(crearCelda(medicamento.getPrecioUnitario().toString(), fontNormal));
                }
                document.add(medicationsTable);
            }

            document.add(new Paragraph("\nResumen de la Compra:").setFont(fontBold).setFontSize(12));
            document.add(new Paragraph("Costo Total: " + costoTotal).setFont(fontNormal));
            Image img = new Image(ImageDataFactory.create(QR));
                img.setHorizontalAlignment(HorizontalAlignment.CENTER);
                img.setWidth(60);
                img.setHeight(60);
                document.add(img);

            document.add(new Paragraph("\nNota: Este documento es una representación impresa de la factura electrónica.")
                    .setFont(fontNormal)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));
        }

        System.out.println("Factura generada exitosamente: " + nombreArchivo);
    } catch (IOException e) {
        System.err.println("Error al generar la factura PDF: " + e.getMessage());
    }
}
    
    public static void generarFacturaPDFConsulta(
        String nombreCliente, 
        String cedulaCliente, 
        String nombreMascota, 
        String especieMascota,
        String nombreVeterinario,
        String motivoConsulta,
        String diagnostico,
        String tratamientoRecomendado,
        BigDecimal costoTotal,
        ArrayList<Medicamento> medicamentosElegidos,
        ArrayList<TipoServicioAdicional> serviciosElegidos
    ) {
    File directorio = new File("facturas");
    if (!directorio.exists()) {
        directorio.mkdirs();
    }

    String nombreArchivo = String.format("facturas/factura_%s_%s_Consulta.pdf",
            LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE),
            nombreMascota);

    try {
        PdfWriter writer = new PdfWriter(nombreArchivo);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf, PageSize.A4)) {
            document.setMargins(30, 30, 30, 30);
            PdfFont fontBold = PdfFontFactory.createFont("Helvetica-Bold");
            PdfFont fontNormal = PdfFontFactory.createFont("Helvetica");

            document.add(new Paragraph(VETERINARIA_NOMBRE)
                    .setFont(fontBold)
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.DARK_GRAY));
            document.add(new Paragraph(VETERINARIA_NIT).setFont(fontNormal).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph(VETERINARIA_DIRECCION).setFont(fontNormal).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph(VETERINARIA_TELEFONO).setFont(fontNormal).setTextAlignment(TextAlignment.CENTER).setMarginBottom(20));
            document.add(new Paragraph("CUFE: " + CUFE).setFont(fontNormal).setTextAlignment(TextAlignment.CENTER));
            String fotoEspecie = "";
            switch (especieMascota.toLowerCase()) {
                case "canino":
                    fotoEspecie = FOTO_CANINO;
                    break;
                case "felino":
                    fotoEspecie = FOTO_FELINO;
                    break;
                case "roedor":
                    fotoEspecie = FOTO_ROEDOR;
                    break;
                case "ave":
                    fotoEspecie = FOTO_AVE;
                    break;
                default:
                    fotoEspecie = null;
            }

            if (fotoEspecie != null && !fotoEspecie.isEmpty()) {
                Image img = new Image(ImageDataFactory.create(fotoEspecie));
                img.setHorizontalAlignment(HorizontalAlignment.CENTER);
                img.setWidth(100);
                img.setHeight(100);
                document.add(img);
            }

            Table customerTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
            customerTable.addCell(crearCelda("Nombre del Cliente:", fontBold));
            customerTable.addCell(crearCelda(nombreCliente, fontNormal));
            customerTable.addCell(crearCelda("Identificación:", fontBold));
            customerTable.addCell(crearCelda(cedulaCliente, fontNormal));
            document.add(customerTable);

            document.add(new Paragraph("\nDatos de la Mascota:").setFont(fontBold).setFontSize(12));
            Table petTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
            petTable.addCell(crearCelda("Nombre de la Mascota:", fontBold));
            petTable.addCell(crearCelda(nombreMascota, fontNormal));
            petTable.addCell(crearCelda("Especie:", fontBold));
            petTable.addCell(crearCelda(especieMascota, fontNormal));
            document.add(petTable);

            document.add(new Paragraph("\nMotivo de la Consulta:").setFont(fontBold).setFontSize(12));
            document.add(new Paragraph(motivoConsulta).setFont(fontNormal));

            document.add(new Paragraph("\nDiagnóstico:").setFont(fontBold).setFontSize(12));
            document.add(new Paragraph(diagnostico).setFont(fontNormal));

            document.add(new Paragraph("\nTratamiento Recomendado:").setFont(fontBold).setFontSize(12));
            document.add(new Paragraph(tratamientoRecomendado).setFont(fontNormal));

            if (medicamentosElegidos != null && !medicamentosElegidos.isEmpty()) {
                document.add(new Paragraph("\nMedicamentos Utilizados:").setFont(fontBold).setFontSize(12));
                Table medicationsTable = new Table(UnitValue.createPercentArray(new float[]{70, 30})).useAllAvailableWidth();
                medicationsTable.addHeaderCell(crearCeldaEncabezado("Medicamento", fontBold));
                medicationsTable.addHeaderCell(crearCeldaEncabezado("Precio", fontBold));
                for (Medicamento medicamento : medicamentosElegidos) {
                    medicationsTable.addCell(crearCelda(medicamento.getNombre(), fontNormal));
                    medicationsTable.addCell(crearCelda(formatearMoneda(medicamento.getPrecioUnitario()), fontNormal));
                }
                document.add(medicationsTable);
            }
            if (serviciosElegidos != null && !serviciosElegidos.isEmpty()) {
                document.add(new Paragraph("\nServicios Adicionales:").setFont(fontBold).setFontSize(12));
                Table servicesTable = new Table(UnitValue.createPercentArray(new float[]{70, 30})).useAllAvailableWidth();
                servicesTable.addHeaderCell(crearCeldaEncabezado("Servicio", fontBold));
                servicesTable.addHeaderCell(crearCeldaEncabezado("Precio", fontBold));
                for (TipoServicioAdicional servicio : serviciosElegidos) {
                    servicesTable.addCell(crearCelda(servicio.getNombre(), fontNormal));
                    servicesTable.addCell(crearCelda(formatearMoneda(BigDecimal.valueOf(servicio.getPrecioBase())), fontNormal));
                }
                document.add(servicesTable);
            }

            document.add(new Paragraph("\nResumen de la Consulta:").setFont(fontBold).setFontSize(12));
            document.add(new Paragraph("Costo Total: " + formatearMoneda(costoTotal)).setFont(fontNormal));
            document.add(new Paragraph("Veterinario: " + nombreVeterinario).setFont(fontNormal));

            document.add(new Paragraph("\nNota: Este documento es una representación impresa de la factura electrónica.")
                    .setFont(fontNormal).setFontSize(10).setTextAlignment(TextAlignment.CENTER));
        }
        System.out.println("Factura generada exitosamente: " + nombreArchivo);
    } catch (IOException e) {
        System.err.println("Error al generar la factura PDF: " + e.getMessage());
    }
}

    public static void generarFacturaPDFProcedimiento(
        String nombreCliente,
        String cedulaCliente,
        String nombreMascota,
        String especieMascota,
        String nombreVeterinario,
        String descripcionServicio,
        BigDecimal costoBase,
        BigDecimal costoFinal,
        ArrayList<Medicamento> medicamentosElegidos,  
        ArrayList<MaterialMedico> materialesElegidos    
) {
    File directorio = new File("facturas");
    if (!directorio.exists()) {
        directorio.mkdirs();
    }

    String nombreArchivo = String.format("facturas/factura_%s_%s_Procedimientos.pdf",
            LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE),
            nombreMascota);

    try {
        PdfWriter writer = new PdfWriter(nombreArchivo);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf, PageSize.A4)) {
            document.setMargins(30, 30, 30, 30);

            PdfFont fontBold = PdfFontFactory.createFont("Helvetica-Bold");
            PdfFont fontNormal = PdfFontFactory.createFont("Helvetica");

           
            document.add(new Paragraph(VETERINARIA_NOMBRE)
                    .setFont(fontBold)
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.DARK_GRAY));
            document.add(new Paragraph(VETERINARIA_NIT).setFont(fontNormal).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph(VETERINARIA_DIRECCION).setFont(fontNormal).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph(VETERINARIA_TELEFONO).setFont(fontNormal).setTextAlignment(TextAlignment.CENTER).setMarginBottom(20));
            document.add(new Paragraph("CUFE: " + CUFE).setFont(fontNormal).setTextAlignment(TextAlignment.CENTER));
            String fotoEspecie = "";
            switch (especieMascota.toLowerCase()) {
                case "canino":
                    fotoEspecie = FOTO_CANINO;
                    break;
                case "felino":
                    fotoEspecie = FOTO_FELINO;
                    break;
                case "roedor":
                    fotoEspecie = FOTO_ROEDOR;
                    break;
                case "ave":
                    fotoEspecie = FOTO_AVE;
                    break;
                default:
                    fotoEspecie = null;
            }

            if (fotoEspecie != null && !fotoEspecie.isEmpty()) {
                Image img = new Image(ImageDataFactory.create(fotoEspecie));
                img.setHorizontalAlignment(HorizontalAlignment.CENTER);
                img.setWidth(100);
                img.setHeight(100);
                document.add(img);
            }

            Table customerTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
            customerTable.addCell(crearCelda("Nombre del Cliente:", fontBold));
            customerTable.addCell(crearCelda(nombreCliente, fontNormal));
            customerTable.addCell(crearCelda("Identificación:", fontBold));
            customerTable.addCell(crearCelda(cedulaCliente, fontNormal));
            document.add(customerTable);

            
            document.add(new Paragraph("\nDetalles del Servicio:").setFont(fontBold).setFontSize(12));
            Table serviceTable = new Table(UnitValue.createPercentArray(new float[]{40, 30, 30})).useAllAvailableWidth();
            serviceTable.addHeaderCell(crearCeldaEncabezado("Descripción", fontBold));
            serviceTable.addHeaderCell(crearCeldaEncabezado("Costo Base", fontBold));
            serviceTable.addHeaderCell(crearCeldaEncabezado("Total", fontBold));
            serviceTable.addCell(crearCelda(descripcionServicio, fontNormal));
            serviceTable.addCell(crearCelda(formatearMoneda(costoBase), fontNormal));
            serviceTable.addCell(crearCelda(formatearMoneda(costoFinal), fontNormal));
            document.add(serviceTable);

            
            if (medicamentosElegidos != null && !medicamentosElegidos.isEmpty()) {
                document.add(new Paragraph("\nMedicamentos Utilizados:").setFont(fontBold).setFontSize(12));
                Table medicationsTable = new Table(UnitValue.createPercentArray(new float[]{70, 30})).useAllAvailableWidth();
                medicationsTable.addHeaderCell(crearCeldaEncabezado("Medicamento", fontBold));
                medicationsTable.addHeaderCell(crearCeldaEncabezado("Precio", fontBold));
                for (Medicamento medicamento : medicamentosElegidos) {
                    medicationsTable.addCell(crearCelda(medicamento.getNombre(), fontNormal));
                    medicationsTable.addCell(crearCelda(formatearMoneda(BigDecimal.valueOf(5.0)), fontNormal));  // Aquí deberías agregar el precio real del medicamento
                }
                document.add(medicationsTable);
            }

            
            if (materialesElegidos != null && !materialesElegidos.isEmpty()) {
                document.add(new Paragraph("\nMateriales Utilizados:").setFont(fontBold).setFontSize(12));
                Table materialsTable = new Table(UnitValue.createPercentArray(new float[]{70, 30})).useAllAvailableWidth();
                materialsTable.addHeaderCell(crearCeldaEncabezado("Material", fontBold));
                materialsTable.addHeaderCell(crearCeldaEncabezado("Precio", fontBold));
                for (MaterialMedico material : materialesElegidos) {
                    materialsTable.addCell(crearCelda(material.getNombre(), fontNormal));
                    materialsTable.addCell(crearCelda(formatearMoneda(material.getPrecioUnitario()).toString(), fontNormal));  
                }
                document.add(materialsTable);
            }

           
            document.add(new Paragraph("\nResumen:").setFont(fontBold).setFontSize(12));
            document.add(new Paragraph("Fecha de Emisión: " + LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                    .setFont(fontNormal));
            document.add(new Paragraph("Veterinario: " + nombreVeterinario).setFont(fontNormal));
            document.add(new Paragraph("Mascota: " + nombreMascota + " (" + especieMascota + ")").setFont(fontNormal));
            document.add(new Paragraph("\nNota: Este documento es una representación impresa de la factura electrónica.")
                    .setFont(fontNormal).setFontSize(10).setTextAlignment(TextAlignment.CENTER));
        }
        System.out.println("Factura generada exitosamente: " + nombreArchivo);
    } catch (IOException e) {
        System.err.println("Error al generar la factura PDF: " + e.getMessage());
    }
}
    
    public static void generarFacturaPDF(
        String nombreCliente,
        String cedulaCliente,
        String nombreMascota,
        String especieMascota,
        String nombreVeterinario,
        String descripcionServicio,
        BigDecimal costoBase,
        BigDecimal costoFinal,
        String desparacitario
) {
    File directorio = new File("facturas");
    if (!directorio.exists()) {
        directorio.mkdirs();
    }

    String nombreArchivo = String.format("facturas/factura_%s_%s_Desparacitacion.pdf",
            LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE),
            nombreMascota);

    try {
        PdfWriter writer = new PdfWriter(nombreArchivo);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf, PageSize.A4)) {
            document.setMargins(30, 30, 30, 30);

            PdfFont fontBold = PdfFontFactory.createFont("Helvetica-Bold");
            PdfFont fontNormal = PdfFontFactory.createFont("Helvetica");

           
            document.add(new Paragraph(VETERINARIA_NOMBRE)
                    .setFont(fontBold)
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.DARK_GRAY));
            document.add(new Paragraph(VETERINARIA_NIT).setFont(fontNormal).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph(VETERINARIA_DIRECCION).setFont(fontNormal).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph(VETERINARIA_TELEFONO).setFont(fontNormal).setTextAlignment(TextAlignment.CENTER).setMarginBottom(20));
            document.add(new Paragraph("CUFE: " + CUFE).setFont(fontNormal).setTextAlignment(TextAlignment.CENTER));
            String fotoEspecie = "";
            switch (especieMascota.toLowerCase()) {
                case "canino":
                    fotoEspecie = FOTO_CANINO;
                    break;
                case "felino":
                    fotoEspecie = FOTO_FELINO;
                    break;
                case "roedor":
                    fotoEspecie = FOTO_ROEDOR;
                    break;
                case "ave":
                    fotoEspecie = FOTO_AVE;
                    break;
                default:
                    fotoEspecie = null;
            }

            if (fotoEspecie != null && !fotoEspecie.isEmpty()) {
                Image img = new Image(ImageDataFactory.create(fotoEspecie));
                img.setHorizontalAlignment(HorizontalAlignment.CENTER);
                img.setWidth(100);
                img.setHeight(100);
                document.add(img);
            }
            
            Table customerTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
            customerTable.addCell(crearCelda("Nombre del Cliente:", fontBold));
            customerTable.addCell(crearCelda(nombreCliente, fontNormal));
            customerTable.addCell(crearCelda("Identificación:", fontBold));
            customerTable.addCell(crearCelda(cedulaCliente, fontNormal));
            document.add(customerTable);

            
            document.add(new Paragraph("\nDetalles del Servicio:").setFont(fontBold).setFontSize(12));
            Table serviceTable = new Table(UnitValue.createPercentArray(new float[]{40, 30, 30})).useAllAvailableWidth();
            serviceTable.addHeaderCell(crearCeldaEncabezado("Descripción", fontBold));
            serviceTable.addHeaderCell(crearCeldaEncabezado("Costo Base", fontBold));
            serviceTable.addHeaderCell(crearCeldaEncabezado("Total", fontBold));
            serviceTable.addCell(crearCelda(descripcionServicio + " - " + desparacitario, fontNormal));
            serviceTable.addCell(crearCelda(formatearMoneda(costoBase), fontNormal));
            serviceTable.addCell(crearCelda(formatearMoneda(costoFinal), fontNormal));
            document.add(serviceTable);

            
            document.add(new Paragraph("\nResumen:").setFont(fontBold).setFontSize(12));
            document.add(new Paragraph("Fecha de Emisión: " + LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                    .setFont(fontNormal));
            document.add(new Paragraph("Veterinario: " + nombreVeterinario).setFont(fontNormal));
            document.add(new Paragraph("Mascota: " + nombreMascota + " (" + especieMascota + ")").setFont(fontNormal));
            document.add(new Paragraph("\nNota: Este documento es una representación impresa de la factura electrónica.")
                    .setFont(fontNormal).setFontSize(10).setTextAlignment(TextAlignment.CENTER));
        }
        System.out.println("Factura generada exitosamente: " + nombreArchivo);
    } catch (IOException e) {
        System.err.println("Error al generar la factura PDF: " + e.getMessage());
    }
}

    private static String formatearMoneda(BigDecimal monto) {
        return String.format("$%.2f", monto.doubleValue());
    }

    private static Cell crearCelda(String texto, PdfFont font) {
        return new Cell()
            .add(new Paragraph(texto).setFont(font))
            .setBorder(null)
            .setPadding(5);
    }

    private static Cell crearCeldaEncabezado(String texto, PdfFont font) {
        return new Cell()
            .add(new Paragraph(texto).setFont(font))
            .setBackgroundColor(ColorConstants.LIGHT_GRAY)
            .setFontSize(10)
            .setTextAlignment(TextAlignment.CENTER)
            .setPadding(5);
    }
}