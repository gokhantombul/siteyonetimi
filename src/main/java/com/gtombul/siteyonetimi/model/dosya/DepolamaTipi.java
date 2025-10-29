package com.gtombul.siteyonetimi.model.dosya;

public enum DepolamaTipi {
    LOCAL, // Sunucunun kendi diskine
    AWS_S3,
    GOOGLE_CLOUD_STORAGE,
    AZURE_BLOB
}