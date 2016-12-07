gsutil -m mv -r gs://static.pratilipi.com/pratilipi-keywords gs://backup.pratilipi.com/static.pratilipi.com-pratilipi-keywords

gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-03
gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-04
gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-05
gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-06
gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-07

gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-$(date +%d)
