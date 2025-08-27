tailwind.config = {
    theme: {
        extend: {
            fontFamily: {
                'inter': ['Inter', 'sans-serif'],
            },
            colors: {
              dark: "var(--color-dark)",
              "dark-secondary": "var(--color-dark-secondary)",
              "dark-card": "var(--color-dark-card)",
              "accent-orange": "var(--color-accent-orange)",
              "accent-yellow": "var(--color-accent-yellow)",
              "text-gray": "var(--color-text-gray)",
            },
            animation: {
                'float': 'float 6s ease-in-out infinite',
                'glow': 'glow 2s ease-in-out infinite alternate',
                'slide-up': 'slideUp 0.6s ease-out forwards',
            },
            keyframes: {
                float: {
                    '0%, 100%': { transform: 'translateY(0px)' },
                    '50%': { transform: 'translateY(-10px)' }
                },
                glow: {
                    'from': { boxShadow: '0 0 20px rgba(10, 81, 199, 0.3)' },
                    'to': { boxShadow: '0 0 40px rgba(10, 81, 199, 0.3, 0.6)' }
                },
                slideUp: {
                    'from': { opacity: '0', transform: 'translateY(50px)' },
                    'to': { opacity: '1', transform: 'translateY(0)' }
                }
            }
        }
    }
}