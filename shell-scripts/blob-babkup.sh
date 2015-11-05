gcloud config set account ``raghu@pratilipi.com''
gcloud config set project devo-pratilipi
sudo gcloud components update
export TZ=Asia/Calcutta
while true
do
    hour=$(date +%H)
    minute=$(date +%M)
    second=$(date +%S)

    #Removing the leading zeros. Otherwise Linux will take it as Octal base.
    hour=${hour#0}
    minute=${minute#0}
    second=${second#0}

    if [ $hour -ge 8 -a $hour -le 20 ]; then
        { #try block
            gsutil -m cp -r gs://devo-pratilipi.appspot.com/user gs://devo-pratilipi.appspot.com/user-$(date)
            logger _BLOB_STORE_BACKUP_SUCCEEDED_
        } || { #catch block
            logger _BLOB_STORE_BACKUP_FAILED_
        }
        let sleeptime="( 60 - $minute ) * 60 - $second"
        echo "Next backup in $sleeptime seconds."
        sleep $sleeptime
    else
        let sleeptime="( ( ( 24 - $hour ) * 60 * 60 ) - ( $minute * 60 ) - $second ) + ( 8 * 60 * 60 ) + 1"
        echo "Next backup in $sleeptime seconds."
        sleep $sleeptime
    fi

    logger _BLOB_STORE_BACKUP_FAILED_PERMANENTLY_

done
