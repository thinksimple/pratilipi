export TZ=Asia/Calcutta

hour=$(date +%H)
minute=$(date +%M)

hour=${hour#0}
minute=${minute#0}

bucket_backup="gs://backup.pratilipi.com"

bucket_static="gs://static.pratilipi.com"
bucket_public="gs://public.pratilipi.com"

backup_folder_static="static.pratilipi.com/yyyy-mm-$(date +%d)/static.pratilipi.com-yyyy-mm-$(date +%d-%H:%M-IST)"
backup_folder_public="public.pratilipi.com/yyyy-mm-$(date +%d)/public.pratilipi.com-yyyy-mm-$(date +%d-%H:%M-IST)"

if [ $hour -ge 8 -a $hour -le 20 -a $minute -eq 0 ]
then

	echo "$(date)" > _backup.start
	gsutil cp _backup.start "$bucket_backup/$backup_folder_static/"
	
	gsutil -m rsync -r $bucket_static "$bucket_backup/$backup_folder_static"
	
	echo "$(date)" > _backup.end
	gsutil cp _backup.end "$bucket_backup/$backup_folder_static/"

	
	echo "$(date)" > _backup.start
	gsutil cp _backup.start "$bucket_backup/$backup_folder_public/"
	
	gsutil -m rsync -r $bucket_public "$bucket_backup/$backup_folder_public"
	
	echo "$(date)" > _backup.end
	gsutil cp _backup.end "$bucket_backup/$backup_folder_public/"


	gsutil cp "$bucket_backup/log" log.old
	echo "Backup Script Run At $(date)" > log
	cat log.old >> log
	gsutil cp log "$bucket_backup/log"


#	gsutil -m du -s $bucket_static "$bucket_backup/$backup_folder_static"
#	gsutil -m du -s $bucket_public "$bucket_backup/$backup_folder_public"
#	gsutil -m du -sh $bucket_static $bucket_public $bucket_backup

fi
