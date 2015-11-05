export TZ=Asia/Calcutta
while true
do
    hour=$(date +%H)
    minute=$(date +%M)

    if [ ${hour#0} -ge 8 -a ${hour#0} -le 23 -a ${hour#0} -e 0 ]; then
        { #try block
            gsutil -m cp -r gs://static.pratilipi.com gs://backup.pratilipi.com/$(`date +%Y-%m-%d`)/static.pratilipi.com-$(`date +%Y-%m-%d-%H:%M-%TZ`)
            logger "_BLOBSTORE_BACKUP_SUCCEEDED_"
        } || { #catch block
            logger "_BLOBSTORE_BACKUP_FAILED_"
        }
    fi

done
