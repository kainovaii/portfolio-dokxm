function videoPlayer() {
    return {
        isPlaying: false,
        play() {
            this.$refs.video.play();
        },
        pause() {
            this.$refs.video.pause();
        },
        togglePlay() {
            if (this.$refs.video.paused) {
                this.play();
            } else {
                this.pause();
            }
        },
        init() {
            this.$refs.video.addEventListener('play', () => {
                this.isPlaying = true;
            });
            this.$refs.video.addEventListener('pause', () => {
                this.isPlaying = false;
            });
        }
    }
}