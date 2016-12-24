# https://cloud.google.com/storage/docs/gsutil/commands/versioning

gsutil versioning set on  gs://static.pratilipi.com
gsutil versioning set off gs://public.pratilipi.com

gsutil versioning set off gs://backup.pratilipi.com
gsutil versioning set off gs://logs.pratilipi.com
gsutil versioning set off gs://prod-pratilipi-backups

gsutil versioning set off gs://staging.prod-pratilipi.appspot.com



# https://cloud.google.com/storage/docs/gsutil/commands/lifecycle
# https://cloud.google.com/storage/docs/managing-lifecycles

gsutil lifecycle set gsutil-lifecycle-backup.json      gs://backup.pratilipi.com
gsutil lifecycle set gsutil-lifecycle-logs.json        gs://logs.pratilipi.com
gsutil lifecycle set gsutil-lifecycle-firebase-db.json gs://prod-pratilipi-backups

gsutil lifecycle set gsutil-lifecycle-staging.json     gs://staging.prod-pratilipi.appspot.com


gsutil lifecycle get gs://static.pratilipi.com
gsutil lifecycle get gs://public.pratilipi.com

gsutil lifecycle get gs://backup.pratilipi.com
gsutil lifecycle get gs://logs.pratilipi.com
gsutil lifecycle get gs://prod-pratilipi-backups

gsutil lifecycle get gs://staging.prod-pratilipi.appspot.com



# https://cloud.google.com/storage/docs/gsutil/commands/cors

gsutil cors set gsutil-cors.json gs://public.pratilipi.com


gsutil cors get gs://static.pratilipi.com
gsutil cors get gs://public.pratilipi.com

gsutil cors get gs://backup.pratilipi.com
gsutil cors get gs://logs.pratilipi.com
gsutil cors get gs://prod-pratilipi-backups

gsutil cors get gs://staging.prod-pratilipi.appspot.com


# https://cloud.google.com/storage/docs/gsutil/commands/setmeta

gsutil -m setmeta -r -h "Cache-Control:public, max-age=31536000" gs://public.pratilipi.com/*

