# additional mount points
dirs755 += "/grubenv"

GRUBENV_DEVICE = "LABEL=grubenv"
WORKSPACE_DEVICE = "LABEL=workspace"

# for production use
#WORKSPACE_OPTIONS = "sync,dirsync,barrier=1,data=journal,journal_checksum"

# for qemu / testing (much faster)
WORKSPACE_OPTIONS = "defaults"

do_install:append () {
    cat >> ${D}${sysconfdir}/fstab <<EOF

# from base-files_%.bbappend
${WORKSPACE_DEVICE}	/sdi	ext4	noatime,${WORKSPACE_OPTIONS}	0 0
${GRUBENV_DEVICE}  /grubenv vfat    defaults 0 0

EOF
}

