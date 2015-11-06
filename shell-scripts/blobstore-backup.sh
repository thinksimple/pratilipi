export TZ=Asia/Calcutta

hour=$(date +%H)
minute=$(date +%M)

hour=${hour#0}
minute=${minute#0}

bucket_backup="gs://backup.pratilipi.com"

bucket_static="gs://static.pratilipi.com"
bucket_public="gs://public.pratilipi.com"

backup_folder_static="static.pratilipi.com/$(date +%Y-%m-%d)/static.pratilipi.com-$(date +%Y-%m-%d-%H:%M-IST)"
backup_folder_public="public.pratilipi.com/$(date +%Y-%m-%d)/public.pratilipi.com-$(date +%Y-%m-%d-%H:%M-IST)"

if [ $hour -ge 8 -a $hour -le 20 -a $minute -eq 0 ]
then
	gsutil -m cp -r $bucket_static "$bucket_backup/$backup_folder_static"
	gsutil -m cp -r $bucket_static "$bucket_backup/$backup_folder_public"
	gsutil -m du -s $bucket_static "$bucket_backup/$backup_folder_static"
	gsutil -m du -s $bucket_public "$bucket_backup/$backup_folder_public"
	gsutil -m du -sh $bucket_static $bucket_public $bucket_backup
	echo "BlobStore backup completed."
fi