PERIODIC_FREQUENCY = 512.
MODULES_FREQUENCY = 512.
TELEMETRY_FREQUENCY = 512.

main_periodic_tid = 1./PERIODIC_FREQUENCY
modules_tid = 1. / MODULES_FREQUENCY
radio_control_tid = 1. / 60.
failsafe_tid = 0.05
electrical_tid = 0.1
telemetry_tid = 1. / TELEMETRY_FREQUENCY

normalizer = 100000.

main = 0
tele = 0
elect = 0
fail = 0
rad = 0
mod = 0

main_periodic_tid = int(main_periodic_tid * normalizer)
telemetry_tid = int(telemetry_tid * normalizer)
electrical_tid = int(electrical_tid * normalizer)
radio_control_tid = int(radio_control_tid * normalizer)
failsafe_tid = int(failsafe_tid * normalizer)
modules_tid = int(modules_tid * normalizer)

i=1
#dssdsdsdfffee
print 'i,main,tele,elect,fail,rad,mod'
while i < 99999:
	update = False
	if  i % main_periodic_tid == 0:
		main+=1
		update = True
	if  i % telemetry_tid == 0:
		tele+=1
		update = True
	if  i % electrical_tid == 0:
		elect+=1
		update = True
	if  i % failsafe_tid == 0:
		fail+=1
		update = True
	if  i % radio_control_tid == 0:
		rad+=1
		update = True
	if  i % modules_tid == 0:
		mod+=1
		update = True
	if update:
		print str(i)+','+str(main)+','+str(tele)+','+str(elect)+','+str(fail)+','+str(rad)+','+str(mod)
	i+=1



		




