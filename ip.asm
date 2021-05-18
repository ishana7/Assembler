PRGAM2      START   0
            USING   *,15
            L       1,DATA1
            AR      1,1
            A       1,=F'5'
            ST      1,RES
            LTORG
RES         DS      3F
DATA1       DC      F'5'
            END