// Smooth scrolling for navigation
/*
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            target.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        }
    });
});
**/

// Scroll animations
const observerOptions = {
    threshold: 0.1,
    rootMargin: '0px 0px -50px 0px'
};


const observer = new IntersectionObserver(function(entries) {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.classList.add('animated');
        }
    });
}, observerOptions);

document.querySelectorAll('.animate-on-scroll').forEach(el => {
    observer.observe(el);
});

// Video card interactions
document.querySelectorAll('.video-card').forEach(card => {
    const video = card.querySelector('video');
    const playButton = card.querySelector('.play-button');

    if (!video || !playButton) return;

    // Style initial
    playButton.style.transition = 'opacity 0.3s ease';
    playButton.style.cursor = 'pointer';
    playButton.style.position = 'absolute';
    playButton.style.top = '50%';
    playButton.style.left = '50%';
    playButton.style.transform = 'translate(-50%, -50%)';
    playButton.style.zIndex = '10';
    playButton.style.opacity = video.paused ? '1' : '0';

    const updatePlayButton = () => {
        playButton.style.opacity = video.paused ? '1' : '0';
        playButton.style.pointerEvents = video.paused ? 'auto' : 'none';
    };

    // Clic sur le bouton play
    playButton.addEventListener('click', (e) => {
        e.stopPropagation();
        // Désactiver temporairement le bouton pour que le clic atteigne la vidéo
        playButton.style.pointerEvents = 'none';
        video.play().catch(err => console.log(err));
    });

    // Clic sur la vidéo pour toggle play/pause
    video.addEventListener('click', () => {
        if (video.paused) video.play();
        else video.pause();
    });

    // Mise à jour bouton sur play/pause
    video.addEventListener('play', updatePlayButton);
    video.addEventListener('pause', updatePlayButton);

    // Initial
    updatePlayButton();
});


// Navigation highlight on scroll
function updateNavigation() {
    const sections = document.querySelectorAll('section[id]');
    const navLinks = document.querySelectorAll('nav a[href^="#"]');

    let current = '';

    sections.forEach(section => {
        const sectionTop = section.getBoundingClientRect().top;
        if (sectionTop <= 100) {
            current = section.getAttribute('id');
        }
    });

    navLinks.forEach(link => {
        link.classList.remove('text-accent-orange');
        link.classList.add('text-text-gray');
        if (link.getAttribute('href') === `#${current}`) {
            link.classList.remove('text-text-gray');
            link.classList.add('text-accent-orange');
        }
    });
}

window.addEventListener('scroll', updateNavigation);
updateNavigation();

// Parallax effect for floating elements
window.addEventListener('scroll', () => {
    const scrolled = window.pageYOffset;
    const parallaxElements = document.querySelectorAll('.animate-float');

    parallaxElements.forEach((element, index) => {
        const speed = 0.5 + (index * 0.2);
        const yPos = -(scrolled * speed);
        element.style.transform = `translateY(${yPos}px) translateY(${Math.sin(Date.now() * 0.001 + index) * 10}px)`;
    });
});

// Skill bars animation
const skillBars = document.querySelectorAll('.skill-bar');
const skillObserver = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            const width = entry.target.style.width;
            entry.target.style.width = '0%';
            setTimeout(() => {
                entry.target.style.width = width;
            }, 500);
        }
    });
}, { threshold: 0.5 });

skillBars.forEach(bar => {
    skillObserver.observe(bar);
});

// Mobile menu toggle
const mobileMenuBtn = document.querySelector('nav button');
const mobileMenu = document.querySelector('nav ul');

if (mobileMenuBtn && mobileMenu) {
    mobileMenuBtn.addEventListener('click', () => {
        mobileMenu.classList.toggle('hidden');
        mobileMenu.classList.toggle('flex');
        mobileMenu.classList.toggle('flex-col');
        mobileMenu.classList.toggle('absolute');
        mobileMenu.classList.toggle('top-full');
        mobileMenu.classList.toggle('left-0');
        mobileMenu.classList.toggle('right-0');
        mobileMenu.classList.toggle('bg-dark');
        mobileMenu.classList.toggle('p-4');
        mobileMenu.classList.toggle('space-y-4');
    });
}

// Loading animation
window.addEventListener('load', () => {
    document.body.style.opacity = '0';
    document.body.style.transition = 'opacity 0.5s ease';

    setTimeout(() => {
        document.body.style.opacity = '1';
    }, 100);
});

// Add cursor trail effect
let mouseX = 0;
let mouseY = 0;
let trailElements = [];

function createTrailElement() {
    const trail = document.createElement('div');
    trail.className = 'fixed w-2 h-2 bg-accent-orange/30 rounded-full pointer-events-none z-40';
    trail.style.left = mouseX + 'px';
    trail.style.top = mouseY + 'px';
    document.body.appendChild(trail);
    trailElements.push(trail);

    setTimeout(() => {
        trail.style.opacity = '0';
        trail.style.transform = 'scale(0)';
        trail.style.transition = 'all 0.5s ease';
    }, 100);

    setTimeout(() => {
        document.body.removeChild(trail);
        trailElements = trailElements.filter(el => el !== trail);
    }, 600);
}

document.addEventListener('mousemove', (e) => {
    mouseX = e.clientX;
    mouseY = e.clientY;

    if (Math.random() > 0.8) {
        createTrailElement();
    }
});

// Video hover effects
document.querySelectorAll('.video-card').forEach(card => {
    card.addEventListener('mouseenter', function() {
        this.style.transform = 'translateY(-10px)';
        this.style.transition = 'all 0.3s ease';
    });

    card.addEventListener('mouseleave', function() {
        this.style.transform = 'translateY(0)';
    });
});

// Intersection Observer for staggered animations
const staggerObserver = new IntersectionObserver((entries) => {
    entries.forEach((entry, index) => {
        if (entry.isIntersecting) {
            setTimeout(() => {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0)';
            }, index * 100);
        }
    });
}, { threshold: 0.1 });

// Apply staggered animation to project cards
document.querySelectorAll('.reel-grid .video-card').forEach(card => {
    card.style.opacity = '0';
    card.style.transform = 'translateY(50px)';
    card.style.transition = 'all 0.6s ease';
    staggerObserver.observe(card);
});

// Add scroll progress indicator
function addScrollProgress() {
    const progressBar = document.createElement('div');
    progressBar.className = 'fixed top-0 left-0 w-full h-1 bg-gradient-to-r from-accent-orange to-accent-yellow z-50 origin-left scale-x-0 transition-transform duration-300';
    document.body.appendChild(progressBar);

    window.addEventListener('scroll', () => {
        const scrollPercent = window.scrollY / (document.body.scrollHeight - window.innerHeight);
        progressBar.style.transform = `scaleX(${scrollPercent})`;
    });
}

addScrollProgress();

// Easter egg - Video editor shortcuts
let shortcuts = [];
const editorShortcuts = [
    { keys: ['j', 'k', 'l'], name: 'Playback Controls' },
    { keys: ['i', 'o'], name: 'In/Out Points' },
    { keys: ['ctrl', 'z'], name: 'Undo' }
];

document.addEventListener('keydown', (e) => {
    shortcuts.push(e.key.toLowerCase());
    if (shortcuts.length > 5) shortcuts.shift();

    // Check for J-K-L sequence (common video editing shortcut)
    if (shortcuts.slice(-3).join('') === 'jkl') {
        // Easter egg: Brief flash effect
        document.body.style.background = 'linear-gradient(45deg, #ff6b35, #ffd23f)';
        setTimeout(() => {
            document.body.style.background = '';
        }, 200);
    }
});