# Copyright (C) 2021 Enrico Jorns <ejo@pengutronix.de>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Boot images"
LICENSE = "MIT"

inherit nopackages deploy

do_fetch[noexec] = "1"
do_patch[noexec] = "1"
do_compile[noexec] = "1"
do_install[noexec] = "1"
deltask do_populate_sysroot

do_deploy[depends] += "\
    dosfstools-native:do_populate_sysroot \
    mtools-native:do_populate_sysroot \
    grub-efi:do_deploy \
    rauc-grubconf:do_deploy"

do_deploy () {
    FATSOURCEDIR="${WORKDIR}/efi-boot/"
    mkdir -p ${FATSOURCEDIR}

    mkdir -p ${FATSOURCEDIR}/EFI/BOOT
    cp ${DEPLOY_DIR_IMAGE}/grub.cfg ${FATSOURCEDIR}/EFI/BOOT/
    cp ${DEPLOY_DIR_IMAGE}/grub-efi-bootx64.efi ${FATSOURCEDIR}/EFI/BOOT/bootx64.efi

    MKDOSFS_EXTRAOPTS="-S 512"
    FATIMG="${WORKDIR}/efi-boot.vfat"
    BLOCKS=32786

    rm -f ${FATIMG}

    mkdosfs -n "BOOT" ${MKDOSFS_EXTRAOPTS} -C ${FATIMG} \
                    ${BLOCKS}
    # Copy FATSOURCEDIR recursively into the image file directly
    mcopy -i ${FATIMG} -s ${FATSOURCEDIR}/* ::/
    chmod 644 ${FATIMG}

    mv ${FATIMG} ${DEPLOYDIR}/
}

do_deploy[cleandirs] += "${WORKDIR}/efi-boot"

addtask deploy after do_install
