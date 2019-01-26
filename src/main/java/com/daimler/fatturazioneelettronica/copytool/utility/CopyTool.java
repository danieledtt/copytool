package com.daimler.fatturazioneelettronica.copytool.utility;

import com.daimler.fatturazioneelettronica.copytool.CopyToolApplication;
import com.daimler.fatturazioneelettronica.copytool.config.CopyToolConfig;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpProgressMonitor;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
public class CopyTool  implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CopyToolConfig copyToolConfig;


    @Override
    public void run(String... args){

        if("sftp".equals(copyToolConfig.getActiveProfile())){
            long time = System.currentTimeMillis();
            logger.info("START");
            JSch jsch=null;
            Session session=null;
            ChannelSftp sftpChannel=null;
            try {
                logger.info("Loaded Config:");
                logger.info("ActiveProfile: [" + copyToolConfig.getActiveProfile() + "]");
                logger.info("SftpHost: [" + copyToolConfig.getSftpHost() + "]");
                logger.info("SftpUser: [" + copyToolConfig.getSftpUser() + "]");
                logger.info("SftpPass: [" + copyToolConfig.getSftpPass() + "]");
                logger.info("SftpPath: [" + copyToolConfig.getSftpPath() + "]");
                logger.info("LocalPath: [" + copyToolConfig.getLocalPath() + "]");
                logger.info("SftpDate: [" + copyToolConfig.getSftpDate() + "]");
                String fromPath=copyToolConfig.getSftpPath().replaceAll("YYYYMMDD",copyToolConfig.getSftpDate());
                logger.info("SftpFromPATH: [" + fromPath + "]");

                jsch = new JSch();
                logger.info("Getting SFTP Connection");
                session = jsch.getSession(copyToolConfig.getSftpUser(), copyToolConfig.getSftpHost());
                session.setPassword(copyToolConfig.getSftpPass());

                java.util.Properties config = new java.util.Properties();
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);

                logger.info("Open SFTP Connection");
                session.connect();

                logger.info("Getting SFTP Channel");
                sftpChannel = (ChannelSftp) session.openChannel("sftp");

                logger.info("Open SFTP Connection");
                sftpChannel.connect();

                logger.info("Transfer Files...");
                sftpChannel.get(fromPath, copyToolConfig.getLocalPath(), new SftpProgressMonitor() {
                    @Override
                    public void init(int i, String s, String s1, long l) {
                        logger.info("Transfer: ["+i+"], from ["+s+"], to ["+s1+"], length ["+l+"]byte");
                    }

                    @Override
                    public boolean count(long l) {
                        logger.info("Transfered: ["+l+"] byte");
                        return false;
                    }

                    @Override
                    public void end() {
                        logger.info("Transfer Ended");
                    }
                }, ChannelSftp.OVERWRITE);
                logger.info("Transfer Complete");


            } catch (Throwable t) {
                logger.error("Errore Generico->", t);
            } finally {

                if(sftpChannel!=null){
                    sftpChannel.disconnect();
                    logger.info("Closed SFTP Channel");
                }

                if(session!=null){
                    session.disconnect();
                    logger.info("Closed SFTP Connection");
                }

                logger.info("STOP - [" + (System.currentTimeMillis() - time) + "]");
            }
        }
        else {
            long time = System.currentTimeMillis();
            logger.info("START");
            FileReader fileReader = null;
            BufferedReader bufferedReader = null;
            String line = null;
            try {
                logger.info("Loaded Config:");
                logger.info("ActiveProfile: [" + copyToolConfig.getActiveProfile() + "]");
                logger.info("FileListSource: [" + copyToolConfig.getFileListSource() + "]");
                logger.info("PathFrom: [" + copyToolConfig.getPathFrom() + "]");
                logger.info("PrefixFrom: [" + copyToolConfig.getPrefixFrom() + "]");
                logger.info("SuffixFrom: [" + copyToolConfig.getSuffixFrom() + "]");
                logger.info("PathTo: [" + copyToolConfig.getPathTo() + "]");
                logger.info("PrefixTo: [" + copyToolConfig.getPrefixTo() + "]");
                logger.info("SuffixTo: [" + copyToolConfig.getSuffixTo() + "]");

                fileReader = new FileReader(copyToolConfig.getFileListSource());
                bufferedReader = new BufferedReader(fileReader);
                line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    logger.info("Line read=[" + line + "]");

                   // String fileInput = copyToolConfig.getPathFrom() + copyToolConfig.getPrefixFrom() + line + copyToolConfig.getSuffixFrom();
                    String fileOutput =null;// copyToolConfig.getPathTo() + copyToolConfig.getPrefixTo() + line + copyToolConfig.getSuffixTo();

                   /* if ("xml".equals(copyToolConfig.getActiveProfile())) {
                        fileInput = line;
                        fileOutput = copyToolConfig.getPathTo() + copyToolConfig.getPrefixTo() + new File(fileInput).getName() + copyToolConfig.getSuffixTo();
                    }*/

                    try {

                        File dir = new File(copyToolConfig.getPathFrom());
                        logger.info("Filter FILE -> "+ copyToolConfig.getPrefixFrom() + line + copyToolConfig.getSuffixFrom());
                        FileFilter fileFilter = new RegexFileFilter( copyToolConfig.getPrefixFrom() + line + copyToolConfig.getSuffixFrom());
                        File[] files = dir.listFiles(fileFilter);

                        for (File src: files) {
                            fileOutput = copyToolConfig.getPathTo() + copyToolConfig.getPrefixTo() + src.getName() +copyToolConfig.getSuffixTo();
                            logger.info("Start copy [" + src.toPath() + "] to [" + fileOutput + "]");
                            Files.move(src.toPath(), new File(fileOutput).toPath(), StandardCopyOption.REPLACE_EXISTING);
                            logger.info("Done");

                        }
                    } catch (Exception e) {
                        logger.error("Errore su copia -> ",e);
                    }

                }


            } catch (Throwable t) {
                logger.error("Errore Generico->", t);
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fileReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                logger.info("STOP - [" + (System.currentTimeMillis() - time) + "]");
            }
        }
    }

}
