FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " file://network.xml"

do_compile:prepend() {
    install -d ${B}/sdi-filesystem
    install -d ${B}/sdi-filesystem/config
    install -m 0600 ${S}/network.xml ${B}/sdi-filesystem/config
}

