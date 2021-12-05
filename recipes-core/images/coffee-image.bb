require recipes-core/images/distro-image-base.bb

# Build recipes-bsp/boot-image/boot-image.bb for the WIC image
do_image_wic[depends] += "boot-image:do_deploy"

# copy microcode update from meta-intel into the rootfs
ROOTFS_POSTPROCESS_COMMAND:append = " install_initramfs;"
install_initramfs() {
    cp ${DEPLOY_DIR_IMAGE}/microcode.cpio ${IMAGE_ROOTFS}/boot/
}

EXTRA_IMAGEDEPENDS += "ovmf"

# gdisk
IMAGE_INSTALL:append = " gptfdisk"

IMAGE_INSTALL:append = " chrony chronyc"

