package com.daimler.fatturazioneelettronica.copytool.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CopyToolConfig {

    public String getActiveProfile() {
        return activeProfile;
    }

    public void setActiveProfile(String activeProfile) {
        this.activeProfile = activeProfile;
    }

    @Value("${spring.profiles.active}")
    String activeProfile;

    @Value("${com.daimler.fatturazioneelettronica.copytool.file-list-source}")
    String fileListSource;

    @Value("${com.daimler.fatturazioneelettronica.copytool.path-from}")
    String pathFrom;
    @Value("${com.daimler.fatturazioneelettronica.copytool.prefix-from}")
    String prefixFrom;
    @Value("${com.daimler.fatturazioneelettronica.copytool.suffix-from}")
    String suffixFrom;

    @Value("${com.daimler.fatturazioneelettronica.copytool.path-to}")
    String pathTo;
    @Value("${com.daimler.fatturazioneelettronica.copytool.prefix-to}")
    String prefixTo;
    @Value("${com.daimler.fatturazioneelettronica.copytool.suffix-to}")
    String suffixTo;

    @Value("${com.daimler.fatturazioneelettronica.copytool.sftpUser}")
    String sftpUser;
    @Value("${com.daimler.fatturazioneelettronica.copytool.sftpHost}")
    String sftpHost;
    @Value("${com.daimler.fatturazioneelettronica.copytool.sftpPass}")
    String sftpPass;
    @Value("${com.daimler.fatturazioneelettronica.copytool.sftpPath}")
    String sftpPath;
    @Value("${com.daimler.fatturazioneelettronica.copytool.localPath}")
    String localPath;
    @Value("${com.daimler.fatturazioneelettronica.copytool.sftpDate}")
    String sftpDate;

    public String getExtensionFileAcctepted() {
        return extensionFileAcctepted;
    }

    public void setExtensionFileAcctepted(String extensionFileAcctepted) {
        this.extensionFileAcctepted = extensionFileAcctepted;
    }

    @Value("${com.daimler.fatturazioneelettronica.copytool.extensionFileAcctepted}")
    String extensionFileAcctepted;

    public String getSftpDate() {
        return sftpDate;
    }

    public void setSftpDate(String sftpDate) {
        this.sftpDate = sftpDate;
    }



    public String getSftpUser() {
        return sftpUser;
    }

    public void setSftpUser(String sftpUser) {
        this.sftpUser = sftpUser;
    }

    public String getSftpHost() {
        return sftpHost;
    }

    public void setSftpHost(String sftpHost) {
        this.sftpHost = sftpHost;
    }

    public String getSftpPass() {
        return sftpPass;
    }

    public void setSftpPass(String sftpPass) {
        this.sftpPass = sftpPass;
    }

    public String getSftpPath() {
        return sftpPath;
    }

    public void setSftpPath(String sftpPath) {
        this.sftpPath = sftpPath;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getFileListSource() {
        return fileListSource;
    }

    public void setFileListSource(String fileListSource) {
        this.fileListSource = fileListSource;
    }

    public String getPathFrom() {
        return pathFrom;
    }

    public void setPathFrom(String pathFrom) {
        this.pathFrom = pathFrom;
    }

    public String getPrefixFrom() {
        return prefixFrom;
    }

    public void setPrefixFrom(String prefixFrom) {
        this.prefixFrom = prefixFrom;
    }

    public String getSuffixFrom() {
        return suffixFrom;
    }

    public void setSuffixFrom(String suffixFrom) {
        this.suffixFrom = suffixFrom;
    }

    public String getPathTo() {
        return pathTo;
    }

    public void setPathTo(String pathTo) {
        this.pathTo = pathTo;
    }

    public String getPrefixTo() {
        return prefixTo;
    }

    public void setPrefixTo(String prefixTo) {
        this.prefixTo = prefixTo;
    }

    public String getSuffixTo() {
        return suffixTo;
    }

    public void setSuffixTo(String suffixTo) {
        this.suffixTo = suffixTo;
    }
}
