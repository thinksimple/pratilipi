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
	gsutil -m rsync -r $bucket_static "$bucket_backup/$backup_folder_static"
	gsutil -m rsync -r $bucket_public "$bucket_backup/$backup_folder_public"

#	gsutil -m du -s $bucket_static "$bucket_backup/$backup_folder_static"
#	gsutil -m du -s $bucket_public "$bucket_backup/$backup_folder_public"
#	gsutil -m du -sh $bucket_static $bucket_public $bucket_backup

	echo "Last Backup Date - $(date)" > $bucket_backup/$backup_folder_public/info
	echo "Last Backup Date - $(date)" > $bucket_backup/$backup_folder_static/info
    
fi