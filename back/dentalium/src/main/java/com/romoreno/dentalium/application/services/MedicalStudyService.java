package com.romoreno.dentalium.application.services;

import com.romoreno.dentalium.application.mappers.MedicalStudyMapperTemp;
import com.romoreno.dentalium.domain.model.DicomImageMedicalStudy;
import com.romoreno.dentalium.domain.model.MedicalStudy;
import com.romoreno.dentalium.domain.port.in.MedicalStudyPort;
import com.romoreno.dentalium.domain.port.out.persistence.MedicalStudyRepository;
import com.romoreno.dentalium.domain.port.out.persistence.StudyTypeRepository;
import com.romoreno.dentalium.domain.port.out.persistence.UserRepository;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.medical_study.dto.DicomResponse;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.medical_study.dto.ListMedicalStudiesResponse;
import com.romoreno.dentalium.infraestructure.config.annotations.GetTransaction;
import com.romoreno.dentalium.infraestructure.config.annotations.PortImplementation;
import io.micrometer.common.util.StringUtils;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReader;
import org.dcm4che3.io.DicomInputStream;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@PortImplementation
public class MedicalStudyService implements MedicalStudyPort {

    final String PATH = String.format("documents%smedical_studies%s", File.separator, File.separator);

    private final MedicalStudyRepository medicalStudyRepository;
    private final StudyTypeRepository studyTypeRepository;
    private final MedicalStudyMapperTemp medicalStudyMapperTemp;
    private final UserRepository userRepository;

    public MedicalStudyService(MedicalStudyRepository medicalStudyRepository, StudyTypeRepository studyTypeRepository,
                               MedicalStudyMapperTemp medicalStudyMapperTemp, UserRepository userRepository) {
        this.medicalStudyRepository = medicalStudyRepository;
        this.studyTypeRepository = studyTypeRepository;
        this.medicalStudyMapperTemp = medicalStudyMapperTemp;
        this.userRepository = userRepository;
    }

    @Override
    @GetTransaction
    public ListMedicalStudiesResponse.MedicalStudy uploadMedicalStudy(byte[] fileByte, String filename, String patientId,
                                      String doctorId, Integer id) throws Exception {

        final var studyTypeOpt = studyTypeRepository.find(id);
        final var doctorEntityOpt = userRepository.findDoctor(doctorId);
        final var patientEntityOpt = userRepository.findPatient(patientId);
        final var extension = filename.substring(filename.lastIndexOf(".") + 1);
        if (studyTypeOpt.isEmpty() || doctorEntityOpt.isEmpty() || patientEntityOpt.isEmpty() || !studyTypeOpt.get().getExtension().equals(extension)) {
            return null;
        }

        final var directory = new File(PATH + patientId);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        var str = PATH + patientId + File.separator + System.currentTimeMillis() + "_" + filename;
        var path = Paths.get(str);
        Files.write(path, fileByte);

        var medicalStudyEntity = medicalStudyMapperTemp
                .toEntity(doctorEntityOpt.get(), patientEntityOpt.get(), studyTypeOpt.get(),
                        new Date(), str);
        var savedEntity = medicalStudyRepository.save(medicalStudyEntity);

        return medicalStudyMapperTemp.toAPI(savedEntity);
    }

    @Override
    public MedicalStudy downloadMedicalStudy(String id) {

        try {
            final var medicalStudyEntity = medicalStudyRepository.findById(Integer.parseInt(id));
            if (medicalStudyEntity.isPresent()) {
                final var pathString = medicalStudyEntity.get().getPath();
                final var path = Path.of(pathString);
                final var filename = pathString.substring(pathString.lastIndexOf("_") + 1);
                return new MedicalStudy(path, filename);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return null;
        }
    }

    @Override
    public DicomImageMedicalStudy downloadDICOMContent(String id) {
        final var medicalStudyOpt = medicalStudyRepository.findById(Integer.parseInt(id));
        if (medicalStudyOpt.isEmpty() || !medicalStudyOpt.get().getStudyTypeEntityId().getExtension().equals("dcm")) {
            return null;
        }
        final var path = Path.of(medicalStudyOpt.get().getPath());

        try (   // Se crea un flujo de entrada
                final var imageInputStream = ImageIO.createImageInputStream(path.toFile());
                // Se indica que lector puede manejar este flujo (el dicomImageReader)
                final var dicomImageReader = (DicomImageReader) ImageIO.getImageReaders(imageInputStream).next();
                final var byteArrayOutputStream = new ByteArrayOutputStream()) {
            // Se le indica al dicomImageReader que debe usar ese flujo de entrada como fuente de datos
            dicomImageReader.setInput(imageInputStream);

            final var bufferedImage = dicomImageReader.read(0);
            ImageIO.write(bufferedImage, "jpeg", byteArrayOutputStream);
            return new DicomImageMedicalStudy(byteArrayOutputStream.toByteArray(), id + ".jpeg");
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public DicomResponse downloadDICOMData(String id) {
        final var medicalStudyOpt = medicalStudyRepository.findById(Integer.parseInt(id));
        if (medicalStudyOpt.isEmpty() || !medicalStudyOpt.get().getStudyTypeEntityId().getExtension().equals("dcm")) {
            return null;
        }

        final var path = Path.of(medicalStudyOpt.get().getPath());

        try (final var dicomInputStream = new DicomInputStream(path.toFile())) {
            final var attributes = dicomInputStream.readDataset();
            final var attributesResponse = this.readAttributes(attributes);


            final var response = new DicomResponse();
            response.setMedicalStudy(medicalStudyMapperTemp.toAPI(medicalStudyOpt.get()));
            response.setDicomAttributes(attributesResponse);
            response.setBase64Content(Base64.getEncoder().encodeToString(downloadDICOMContent(id).getContent()));
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ListMedicalStudiesResponse listMedicalStudies(String patientId) {
        final var budgetList = medicalStudyRepository.findAllByPatientId(patientId);
        return medicalStudyMapperTemp.toAPI(budgetList, patientId);
    }

    @Override
    public boolean deleteMedicalStudy(Integer id) {
        final var medicalStudyOpt = medicalStudyRepository.findById(id);
        if (medicalStudyOpt.isPresent()) {
            final var medicalStudy = medicalStudyOpt.get();
            final var path = Path.of(medicalStudy.getPath());
            final var deleted = path.toFile().delete();
            if (deleted) {
                medicalStudyRepository.delete(id);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private List<DicomResponse.DicomAttributes> readAttributes(Attributes attributes) {
        final var dicomAttributes = new ArrayList<DicomResponse.DicomAttributes>();

        final var fieldsTagClass = Tag.class.getFields();
        for (final var field : fieldsTagClass) {
            try {
                final var fieldName = field.getName();
                final var fieldCode = field.getInt(null); // Valor por el que se identifica el nombre del campo
                if (attributes.contains(fieldCode)) {
                    final var value = attributes.getString(fieldCode);
                    if (StringUtils.isNotBlank(value)) {
                        dicomAttributes.add(new DicomResponse.DicomAttributes(fieldName, value));
                    }
                }
            } catch (Exception e) {
            }
        }
        return dicomAttributes.stream().sorted(Comparator.comparing(DicomResponse.DicomAttributes::getValue))
                .toList();
    }
}
