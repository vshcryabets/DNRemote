#
# Regular cron jobs for the dnremoteserver package
#
0 4	* * *	root	[ -x /usr/bin/dnremoteserver_maintenance ] && /usr/bin/dnremoteserver_maintenance
