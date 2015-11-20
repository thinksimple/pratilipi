export TZ=Asia/Calcutta

hour=$(date +%H)
minute=$(date +%M)

hour=${hour#0}
minute=${minute#0}

bucket_backup="gs://backup.pratilipi.com"

bucket_static="gs://static.pratilipi.com"
bucket_public="gs://public.pratilipi.com"

backup_folder_static="static.pratilipi.com/yyyy-mm-$(date +%d)/static.pratilipi.com-$(date +%H:%M-IST)"
backup_folder_public="public.pratilipi.com/yyyy-mm-$(date +%d)/public.pratilipi.com-$(date +%H:%M-IST)"

if [ $hour -ge 8 -a $hour -le 20 -a $minute -eq 0 ]
then

	echo "Initiated backup at : $(date)" > info
	gsutil -m rsync -r $bucket_static "$bucket_backup/$backup_folder_static"
	gsutil -m rsync -r $bucket_public "$bucket_backup/$backup_folder_public"
	echo "Backup was successfully completed at : $(date)" >> info

	gsutil cp info "$bucket_backup/$backup_folder_static/"
	gsutil cp info "$bucket_backup/$backup_folder_public/"

	echo "Last Backup taken at : $(date)" > last_backup_info
	gsutil cp last_backup_info "$bucket_backup/static.pratilipi.com/"
	gsutil cp last_backup_info "$bucket_backup/public.pratilipi.com/"

	echo "Backup taken at : $(date)" >> logs
	gsutil cp logs "$bucket_backup/static.pratilipi.com/"
	gsutil cp logs "$bucket_backup/public.pratilipi.com/"

#	gsutil -m du -s $bucket_static "$bucket_backup/$backup_folder_static"
#	gsutil -m du -s $bucket_public "$bucket_backup/$backup_folder_public"
#	gsutil -m du -sh $bucket_static $bucket_public $bucket_backup

fi
