FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
    file://con-eth0.nmconnection \
"

do_install:append() {
	install -d ${D}${sysconfdir}/NetworkManager/system-connections/
	install -m 0600 ${WORKDIR}/con-eth0.nmconnection ${D}${sysconfdir}/NetworkManager/system-connections/con-eth0.nmconnection
}

