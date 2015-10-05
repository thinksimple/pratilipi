export TZ=Asia/Calcutta
message="Blobstore backup failure occured at : $(date)."
printf 'Subject: Backup Failure\r\n\r\n%s\r\n\r\n' "$message" | /usr/sbin/sendmail raghu@pratilipi.com
printf 'Subject: Backup Failure\r\n\r\n%s\r\n\r\n' "$message" | /usr/sbin/sendmail prashant@pratilipi.com