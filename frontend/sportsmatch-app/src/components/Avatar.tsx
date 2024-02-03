

interface AvatarProps {
    src: string;
    alt: string;
}

const Avatar: React.FC<AvatarProps> = ({src, alt}) => (
    <div className="avatar">
        <img src={src} alt={alt} className="avatar-image" />
    </div>
);

export default Avatar;