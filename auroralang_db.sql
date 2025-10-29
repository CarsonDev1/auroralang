-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 26, 2025 lúc 06:04 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `auroralang_db`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `classes`
--

CREATE TABLE `classes` (
  `class_id` int(11) NOT NULL,
  `class_code` varchar(50) NOT NULL,
  `class_name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `course_id` int(11) NOT NULL,
  `teacher_id` int(11) NOT NULL,
  `manager_id` int(11) DEFAULT NULL COMMENT 'Assigned manager',
  `start_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  `max_students` int(11) DEFAULT 30,
  `current_students` int(11) DEFAULT 0,
  `schedule_info` text DEFAULT NULL COMMENT 'Class schedule description',
  `room_info` varchar(255) DEFAULT NULL,
  `status` enum('PLANNED','ONGOING','COMPLETED','CANCELLED') DEFAULT 'PLANNED',
  `is_public` tinyint(1) DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `classes`
--

INSERT INTO `classes` (`class_id`, `class_code`, `class_name`, `description`, `course_id`, `teacher_id`, `manager_id`, `start_date`, `end_date`, `max_students`, `current_students`, `schedule_info`, `room_info`, `status`, `is_public`, `created_at`, `updated_at`) VALUES
(1, 'CLASS_JPN101_001', 'Japanese Beginners - Morning Class', 'Morning class for JPN101 course', 1, 10, 3, '2024-04-01', '2024-05-31', 20, 15, 'Monday, Wednesday, Friday 9:00-10:30 AM', 'Room A101', 'ONGOING', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(2, 'CLASS_JPN101_002', 'Japanese Beginners - Evening Class', 'Evening class for JPN101 course', 1, 11, 3, '2024-04-01', '2024-05-31', 20, 12, 'Tuesday, Thursday 7:00-8:30 PM', 'Room A102', 'ONGOING', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(3, 'CLASS_JPN102_001', 'Basic Grammar - Weekend Class', 'Weekend class for JPN102 course', 2, 12, 4, '2024-04-06', '2024-06-15', 15, 10, 'Saturday, Sunday 2:00-4:00 PM', 'Room B201', 'ONGOING', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(4, 'CLASS_JPN201_001', 'Elementary Conversation - Morning Class', 'Morning conversation class', 4, 13, 4, '2024-04-01', '2024-06-30', 18, 14, 'Monday, Wednesday, Friday 10:00-11:30 AM', 'Room C301', 'ONGOING', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(5, 'CLASS_JLPTN5_001', 'JLPT N5 Preparation - Intensive', 'Intensive JLPT N5 preparation class', 13, 14, 5, '2024-04-01', '2024-07-31', 25, 20, 'Monday to Friday 6:00-8:00 PM', 'Room D401', 'ONGOING', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `class_enrollments`
--

CREATE TABLE `class_enrollments` (
  `class_enrollment_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  `enrollment_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `enrollment_type` enum('SELF','TEACHER_ADDED','MANAGER_ASSIGNED','IMPORT') DEFAULT 'SELF',
  `status` enum('ACTIVE','COMPLETED','DROPPED','SUSPENDED') DEFAULT 'ACTIVE',
  `attendance_rate` decimal(5,2) DEFAULT 0.00,
  `final_grade` decimal(5,2) DEFAULT NULL,
  `enrolled_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `class_enrollments`
--

INSERT INTO `class_enrollments` (`class_enrollment_id`, `user_id`, `class_id`, `enrollment_date`, `enrollment_type`, `status`, `attendance_rate`, `final_grade`, `enrolled_by`) VALUES
(1, 15, 1, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 95.00, NULL, 15),
(2, 16, 1, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 85.00, NULL, 16),
(3, 17, 1, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 100.00, NULL, 17),
(4, 18, 1, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 90.00, NULL, 18),
(5, 19, 1, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 80.00, NULL, 19),
(6, 20, 2, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 75.00, NULL, 20),
(7, 21, 2, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 100.00, NULL, 21),
(8, 22, 2, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 90.00, NULL, 22),
(9, 23, 2, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 85.00, NULL, 23),
(10, 15, 3, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 100.00, NULL, 15),
(11, 20, 3, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 95.00, NULL, 20),
(12, 21, 3, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 90.00, NULL, 21),
(13, 24, 3, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 85.00, NULL, 24),
(14, 16, 4, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 95.00, NULL, 16),
(15, 17, 4, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 100.00, NULL, 17),
(16, 22, 4, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 80.00, NULL, 22),
(17, 23, 4, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 90.00, NULL, 23),
(18, 18, 5, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 100.00, NULL, 18),
(19, 19, 5, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 95.00, NULL, 19),
(20, 20, 5, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 90.00, NULL, 20),
(21, 21, 5, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 85.00, NULL, 21),
(22, 22, 5, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 100.00, NULL, 22),
(23, 23, 5, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 95.00, NULL, 23),
(24, 24, 5, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 80.00, NULL, 24);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `class_flashcards`
--

CREATE TABLE `class_flashcards` (
  `class_flashcard_id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  `flashcard_id` int(11) DEFAULT NULL COMMENT 'Reference to course flashcard if using',
  `front_text` varchar(500) NOT NULL,
  `back_text` text NOT NULL,
  `is_active` tinyint(1) DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `class_lessons`
--

CREATE TABLE `class_lessons` (
  `class_lesson_id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  `lesson_id` int(11) DEFAULT NULL COMMENT 'Reference to course lesson if using',
  `lesson_name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `content` text DEFAULT NULL,
  `lesson_order` int(11) DEFAULT 0,
  `is_active` tinyint(1) DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `class_sessions`
--

CREATE TABLE `class_sessions` (
  `session_id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  `lesson_id` int(11) DEFAULT NULL,
  `session_number` int(11) NOT NULL,
  `session_name` varchar(255) NOT NULL,
  `session_date` date NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `online_meeting_url` varchar(500) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `status` enum('SCHEDULED','ONGOING','COMPLETED','CANCELLED') DEFAULT 'SCHEDULED',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `class_sessions`
--

INSERT INTO `class_sessions` (`session_id`, `class_id`, `lesson_id`, `session_number`, `session_name`, `session_date`, `start_time`, `end_time`, `location`, `online_meeting_url`, `description`, `status`, `created_at`, `updated_at`) VALUES
(1, 1, 1, 1, 'Introduction to Hiragana', '2024-04-01', '09:00:00', '10:30:00', 'Room A101', 'https://meet.google.com/session1', 'First session introducing hiragana basics', 'COMPLETED', '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(2, 1, 2, 2, 'Hiragana Vowels', '2024-04-03', '09:00:00', '10:30:00', 'Room A101', 'https://meet.google.com/session2', 'Learning the five basic vowels', 'COMPLETED', '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(3, 1, 3, 3, 'Hiragana K-line', '2024-04-05', '09:00:00', '10:30:00', 'Room A101', 'https://meet.google.com/session3', 'Mastering the K-line characters', 'COMPLETED', '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(4, 1, 4, 4, 'Hiragana S-line', '2024-04-08', '09:00:00', '10:30:00', 'Room A101', 'https://meet.google.com/session4', 'Learning the S-line characters', 'SCHEDULED', '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(5, 1, 5, 5, 'Introduction to Katakana', '2024-04-10', '09:00:00', '10:30:00', 'Room A101', 'https://meet.google.com/session5', 'Introduction to katakana writing system', 'SCHEDULED', '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(6, 3, 7, 1, 'Basic Sentence Structure', '2024-04-06', '14:00:00', '16:00:00', 'Room B201', 'https://meet.google.com/session6', 'Understanding Japanese sentence patterns', 'COMPLETED', '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(7, 3, 8, 2, 'Particles は and が', '2024-04-07', '14:00:00', '16:00:00', 'Room B201', 'https://meet.google.com/session7', 'Learning topic and subject particles', 'COMPLETED', '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(8, 3, 9, 3, 'Particle を', '2024-04-13', '14:00:00', '16:00:00', 'Room B201', 'https://meet.google.com/session8', 'Understanding object particle', 'SCHEDULED', '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(9, 3, 10, 4, 'Present Tense Verbs', '2024-04-14', '14:00:00', '16:00:00', 'Room B201', 'https://meet.google.com/session9', 'Learning present tense conjugations', 'SCHEDULED', '2025-10-26 17:02:58', '2025-10-26 17:02:58');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `class_tests`
--

CREATE TABLE `class_tests` (
  `class_test_id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  `test_id` int(11) DEFAULT NULL COMMENT 'Reference to course test if using',
  `test_name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `duration_minutes` int(11) DEFAULT NULL,
  `available_from` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `available_until` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_active` tinyint(1) DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `courses`
--

CREATE TABLE `courses` (
  `course_id` int(11) NOT NULL,
  `course_code` varchar(50) NOT NULL,
  `course_name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `thumbnail_url` varchar(500) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `level` enum('BEGINNER','ELEMENTARY','INTERMEDIATE','ADVANCED','EXPERT') NOT NULL,
  `language` varchar(50) DEFAULT 'Japanese',
  `duration_weeks` int(11) DEFAULT NULL COMMENT 'Estimated duration in weeks',
  `price` decimal(10,2) DEFAULT 0.00,
  `discount_price` decimal(10,2) DEFAULT NULL,
  `is_public` tinyint(1) DEFAULT 0 COMMENT 'Public courses visible to guests',
  `is_active` tinyint(1) DEFAULT 1,
  `status` enum('DRAFT','PUBLISHED','ARCHIVED') DEFAULT 'DRAFT',
  `total_enrollments` int(11) DEFAULT 0,
  `total_lessons` int(11) DEFAULT 0,
  `total_flashcards` int(11) DEFAULT 0,
  `total_tests` int(11) DEFAULT 0,
  `average_rating` decimal(3,2) DEFAULT 0.00,
  `total_reviews` int(11) DEFAULT 0,
  `created_by` int(11) NOT NULL COMMENT 'Expert ID',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `published_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `courses`
--

INSERT INTO `courses` (`course_id`, `course_code`, `course_name`, `description`, `thumbnail_url`, `category_id`, `level`, `language`, `duration_weeks`, `price`, `discount_price`, `is_public`, `is_active`, `status`, `total_enrollments`, `total_lessons`, `total_flashcards`, `total_tests`, `average_rating`, `total_reviews`, `created_by`, `created_at`, `updated_at`, `published_at`) VALUES
(1, 'JPN101', 'Japanese for Beginners - Hiragana & Katakana', 'Learn the fundamentals of Japanese writing systems. Master Hiragana and Katakana characters with interactive exercises and pronunciation guides.', '/images/course-thumbnails/jpn101.jpg', 1, 'BEGINNER', 'Japanese', 8, 299000.00, 199000.00, 1, 1, 'PUBLISHED', 45, 12, 50, 3, 4.50, 23, 6, '2025-10-26 17:02:58', '2025-10-26 17:02:58', '2024-01-15 03:00:00'),
(2, 'JPN102', 'Basic Japanese Grammar', 'Essential grammar patterns for beginners. Learn sentence structure, particles, and basic verb conjugations.', '/images/course-thumbnails/jpn102.jpg', 2, 'BEGINNER', 'Japanese', 10, 399000.00, 299000.00, 1, 1, 'PUBLISHED', 38, 15, 60, 4, 4.30, 18, 6, '2025-10-26 17:02:58', '2025-10-26 17:02:58', '2024-01-20 03:00:00'),
(3, 'JPN103', 'Essential Japanese Vocabulary', 'Build your vocabulary with 500+ essential Japanese words. Learn with flashcards, audio, and example sentences.', '/images/course-thumbnails/jpn103.jpg', 3, 'BEGINNER', 'Japanese', 6, 199000.00, 149000.00, 1, 1, 'PUBLISHED', 52, 10, 100, 2, 4.70, 31, 7, '2025-10-26 17:02:58', '2025-10-26 17:02:58', '2024-02-01 03:00:00'),
(4, 'JPN201', 'Elementary Japanese Conversation', 'Practice speaking Japanese with common daily conversations. Learn polite forms and basic social interactions.', '/images/course-thumbnails/jpn201.jpg', 4, 'ELEMENTARY', 'Japanese', 12, 499000.00, 399000.00, 1, 1, 'PUBLISHED', 28, 18, 40, 5, 4.40, 15, 7, '2025-10-26 17:02:58', '2025-10-26 17:02:58', '2024-02-10 03:00:00'),
(5, 'JPN202', 'Japanese Numbers and Time', 'Master Japanese numbers, time expressions, and calendar vocabulary. Essential for daily life in Japan.', '/images/course-thumbnails/jpn202.jpg', 3, 'ELEMENTARY', 'Japanese', 8, 299000.00, 249000.00, 1, 1, 'PUBLISHED', 35, 12, 30, 3, 4.60, 20, 8, '2025-10-26 17:02:58', '2025-10-26 17:02:58', '2024-02-15 03:00:00'),
(6, 'JPN203', 'Japanese Family and Relationships', 'Learn vocabulary and expressions related to family, friends, and social relationships in Japanese culture.', '/images/course-thumbnails/jpn203.jpg', 3, 'ELEMENTARY', 'Japanese', 10, 399000.00, 349000.00, 1, 1, 'PUBLISHED', 22, 14, 45, 4, 4.20, 12, 8, '2025-10-26 17:02:58', '2025-10-26 17:02:58', '2024-02-20 03:00:00'),
(7, 'JPN301', 'Intermediate Japanese Grammar', 'Advanced grammar patterns, conditional forms, and complex sentence structures for intermediate learners.', '/images/course-thumbnails/jpn301.jpg', 2, 'INTERMEDIATE', 'Japanese', 16, 699000.00, 599000.00, 1, 1, 'PUBLISHED', 18, 24, 35, 6, 4.80, 14, 9, '2025-10-26 17:02:58', '2025-10-26 17:02:58', '2024-03-01 03:00:00'),
(8, 'JPN302', 'Business Japanese', 'Professional Japanese for workplace communication. Learn keigo (honorific language) and business etiquette.', '/images/course-thumbnails/jpn302.jpg', 4, 'INTERMEDIATE', 'Japanese', 14, 799000.00, 699000.00, 1, 1, 'PUBLISHED', 12, 20, 25, 5, 4.90, 8, 9, '2025-10-26 17:02:58', '2025-10-26 17:02:58', '2024-03-05 03:00:00'),
(9, 'JPN303', 'Japanese Culture and Society', 'Understand Japanese culture, traditions, and social norms through language learning.', '/images/course-thumbnails/jpn303.jpg', 1, 'INTERMEDIATE', 'Japanese', 12, 599000.00, 499000.00, 1, 1, 'PUBLISHED', 25, 16, 20, 4, 4.50, 16, 6, '2025-10-26 17:02:58', '2025-10-26 17:02:58', '2024-03-10 03:00:00'),
(10, 'JPN401', 'Advanced Japanese Literature', 'Read and analyze Japanese literature. Study classical and modern Japanese texts with detailed explanations.', '/images/course-thumbnails/jpn401.jpg', 1, 'ADVANCED', 'Japanese', 20, 999000.00, 899000.00, 1, 1, 'PUBLISHED', 8, 30, 15, 8, 4.70, 6, 7, '2025-10-26 17:02:58', '2025-10-26 17:02:58', '2024-03-15 03:00:00'),
(11, 'JPN402', 'Japanese Media and News', 'Understand Japanese news, TV shows, and media content. Improve listening and reading comprehension.', '/images/course-thumbnails/jpn402.jpg', 4, 'ADVANCED', 'Japanese', 18, 899000.00, 799000.00, 1, 1, 'PUBLISHED', 10, 25, 10, 6, 4.60, 7, 8, '2025-10-26 17:02:58', '2025-10-26 17:02:58', '2024-03-20 03:00:00'),
(12, 'JLPTN5', 'JLPT N5 Preparation Course', 'Complete preparation for Japanese Language Proficiency Test N5 level. Practice tests, vocabulary, and grammar.', '/images/course-thumbnails/jlptn5.jpg', 5, 'BEGINNER', 'Japanese', 16, 599000.00, 499000.00, 1, 1, 'PUBLISHED', 40, 20, 80, 10, 4.40, 25, 6, '2025-10-26 17:02:58', '2025-10-26 17:02:58', '2024-01-25 03:00:00'),
(13, 'JLPTN4', 'JLPT N4 Preparation Course', 'Complete preparation for Japanese Language Proficiency Test N4 level. Practice tests, vocabulary, and grammar.', '/images/course-thumbnails/jlptn4.jpg', 5, 'ELEMENTARY', 'Japanese', 20, 799000.00, 699000.00, 1, 1, 'PUBLISHED', 25, 25, 100, 12, 4.60, 18, 7, '2025-10-26 17:02:58', '2025-10-26 17:02:58', '2024-02-05 03:00:00'),
(14, 'JLPTN3', 'JLPT N3 Preparation Course', 'Complete preparation for Japanese Language Proficiency Test N3 level. Practice tests, vocabulary, and grammar.', '/images/course-thumbnails/jlptn3.jpg', 5, 'INTERMEDIATE', 'Japanese', 24, 999000.00, 899000.00, 1, 1, 'PUBLISHED', 15, 30, 120, 15, 4.80, 12, 8, '2025-10-26 17:02:58', '2025-10-26 17:02:58', '2024-02-25 03:00:00'),
(15, 'JLPTN2', 'JLPT N2 Preparation Course', 'Complete preparation for Japanese Language Proficiency Test N2 level. Practice tests, vocabulary, and grammar.', '/images/course-thumbnails/jlptn2.jpg', 5, 'ADVANCED', 'Japanese', 28, 1299000.00, 1199000.00, 1, 1, 'PUBLISHED', 8, 35, 150, 18, 4.90, 6, 9, '2025-10-26 17:02:58', '2025-10-26 17:02:58', '2024-03-25 03:00:00');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `course_categories`
--

CREATE TABLE `course_categories` (
  `category_id` int(11) NOT NULL,
  `category_name` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `parent_category_id` int(11) DEFAULT NULL,
  `display_order` int(11) DEFAULT 0,
  `is_active` tinyint(1) DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `course_categories`
--

INSERT INTO `course_categories` (`category_id`, `category_name`, `description`, `parent_category_id`, `display_order`, `is_active`, `created_at`, `updated_at`) VALUES
(1, 'Japanese Language', 'Japanese language learning courses', NULL, 0, 1, '2025-10-26 17:02:40', '2025-10-26 17:02:40'),
(2, 'Grammar', 'Grammar focused courses', NULL, 0, 1, '2025-10-26 17:02:40', '2025-10-26 17:02:40'),
(3, 'Vocabulary', 'Vocabulary building courses', NULL, 0, 1, '2025-10-26 17:02:40', '2025-10-26 17:02:40'),
(4, 'Conversation', 'Speaking and conversation practice', NULL, 0, 1, '2025-10-26 17:02:40', '2025-10-26 17:02:40'),
(5, 'JLPT Preparation', 'Japanese Language Proficiency Test preparation', NULL, 0, 1, '2025-10-26 17:02:40', '2025-10-26 17:02:40');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `course_enrollments`
--

CREATE TABLE `course_enrollments` (
  `enrollment_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `enrollment_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `enrollment_type` enum('SELF','MANAGER_ASSIGNED','ADMIN_ASSIGNED') DEFAULT 'SELF',
  `status` enum('ACTIVE','COMPLETED','DROPPED','EXPIRED') DEFAULT 'ACTIVE',
  `progress_percentage` decimal(5,2) DEFAULT 0.00,
  `completed_lessons` int(11) DEFAULT 0,
  `completed_tests` int(11) DEFAULT 0,
  `last_accessed_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `completion_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `certificate_url` varchar(500) DEFAULT NULL,
  `enrolled_by` int(11) DEFAULT NULL COMMENT 'Who enrolled the user'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `course_enrollments`
--

INSERT INTO `course_enrollments` (`enrollment_id`, `user_id`, `course_id`, `enrollment_date`, `enrollment_type`, `status`, `progress_percentage`, `completed_lessons`, `completed_tests`, `last_accessed_at`, `completion_date`, `certificate_url`, `enrolled_by`) VALUES
(1, 15, 1, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 25.00, 3, 1, '2024-04-05 03:30:00', '0000-00-00 00:00:00', NULL, 15),
(2, 16, 1, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 15.00, 2, 0, '2024-04-03 02:15:00', '0000-00-00 00:00:00', NULL, 16),
(3, 17, 1, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 35.00, 4, 1, '2024-04-08 04:00:00', '0000-00-00 00:00:00', NULL, 17),
(4, 18, 1, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 20.00, 2, 0, '2024-04-01 02:30:00', '0000-00-00 00:00:00', NULL, 18),
(5, 19, 1, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 30.00, 3, 1, '2024-04-06 03:45:00', '0000-00-00 00:00:00', NULL, 19),
(6, 15, 2, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 40.00, 2, 1, '2024-04-07 09:00:00', '0000-00-00 00:00:00', NULL, 15),
(7, 20, 2, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 20.00, 1, 0, '2024-04-06 07:30:00', '0000-00-00 00:00:00', NULL, 20),
(8, 21, 2, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 60.00, 3, 1, '2024-04-08 08:45:00', '0000-00-00 00:00:00', NULL, 21),
(9, 16, 4, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 30.00, 2, 0, '2024-04-05 04:30:00', '0000-00-00 00:00:00', NULL, 16),
(10, 17, 4, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 45.00, 3, 1, '2024-04-08 05:00:00', '0000-00-00 00:00:00', NULL, 17),
(11, 22, 4, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 25.00, 1, 0, '2024-04-03 03:15:00', '0000-00-00 00:00:00', NULL, 22),
(12, 18, 13, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 50.00, 2, 1, '2024-04-05 12:30:00', '0000-00-00 00:00:00', NULL, 18),
(13, 19, 13, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 35.00, 1, 0, '2024-04-02 11:45:00', '0000-00-00 00:00:00', NULL, 19),
(14, 23, 13, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 70.00, 3, 2, '2024-04-08 13:00:00', '0000-00-00 00:00:00', NULL, 23),
(15, 24, 13, '2025-10-26 17:02:58', 'SELF', 'ACTIVE', 40.00, 2, 1, '2024-04-06 12:15:00', '0000-00-00 00:00:00', NULL, 24);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `course_reviews`
--

CREATE TABLE `course_reviews` (
  `review_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `rating` int(11) NOT NULL CHECK (`rating` between 1 and 5),
  `review_text` text DEFAULT NULL,
  `is_approved` tinyint(1) DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `course_settings`
--

CREATE TABLE `course_settings` (
  `setting_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `setting_key` varchar(100) NOT NULL,
  `setting_value` text DEFAULT NULL,
  `description` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `daily_study_logs`
--

CREATE TABLE `daily_study_logs` (
  `log_id` bigint(20) NOT NULL,
  `user_id` int(11) NOT NULL,
  `study_date` date NOT NULL,
  `lessons_completed` int(11) DEFAULT 0,
  `flashcards_reviewed` int(11) DEFAULT 0,
  `tests_taken` int(11) DEFAULT 0,
  `time_spent_seconds` int(11) DEFAULT 0,
  `xp_earned` int(11) DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `daily_study_logs`
--

INSERT INTO `daily_study_logs` (`log_id`, `user_id`, `study_date`, `lessons_completed`, `flashcards_reviewed`, `tests_taken`, `time_spent_seconds`, `xp_earned`, `created_at`) VALUES
(1, 15, '2024-04-08', 1, 25, 0, 1800, 150, '2025-10-26 17:02:58'),
(2, 16, '2024-04-08', 0, 15, 1, 1200, 100, '2025-10-26 17:02:58'),
(3, 17, '2024-04-08', 2, 35, 0, 2400, 200, '2025-10-26 17:02:58'),
(4, 18, '2024-04-08', 1, 20, 1, 2100, 175, '2025-10-26 17:02:58'),
(5, 19, '2024-04-08', 0, 10, 0, 900, 75, '2025-10-26 17:02:58'),
(6, 20, '2024-04-08', 1, 30, 0, 1500, 125, '2025-10-26 17:02:58'),
(7, 21, '2024-04-08', 1, 40, 0, 2700, 225, '2025-10-26 17:02:58'),
(8, 22, '2024-04-08', 0, 18, 0, 1350, 112, '2025-10-26 17:02:58'),
(9, 23, '2024-04-08', 2, 28, 1, 1950, 162, '2025-10-26 17:02:58'),
(10, 24, '2024-04-08', 0, 12, 0, 1050, 87, '2025-10-26 17:02:58');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `flashcards`
--

CREATE TABLE `flashcards` (
  `flashcard_id` int(11) NOT NULL,
  `course_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT 'NULL for course flashcards, NOT NULL for personal flashcards',
  `front_text` varchar(500) NOT NULL COMMENT 'Question/Word/Kanji',
  `back_text` text NOT NULL COMMENT 'Answer/Meaning',
  `front_audio_url` varchar(500) DEFAULT NULL,
  `back_audio_url` varchar(500) DEFAULT NULL,
  `front_image_url` varchar(500) DEFAULT NULL,
  `back_image_url` varchar(500) DEFAULT NULL,
  `pronunciation` varchar(255) DEFAULT NULL COMMENT 'Hiragana/Romaji',
  `example_sentence` text DEFAULT NULL,
  `note` text DEFAULT NULL,
  `difficulty` enum('EASY','MEDIUM','HARD') DEFAULT 'MEDIUM',
  `tags` varchar(500) DEFAULT NULL COMMENT 'Comma-separated tags',
  `is_public` tinyint(1) DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `flashcards`
--

INSERT INTO `flashcards` (`flashcard_id`, `course_id`, `user_id`, `front_text`, `back_text`, `front_audio_url`, `back_audio_url`, `front_image_url`, `back_image_url`, `pronunciation`, `example_sentence`, `note`, `difficulty`, `tags`, `is_public`, `created_at`, `updated_at`) VALUES
(1, 1, NULL, 'あ', 'a', 'https://audio.com/hiragana_a.mp3', 'https://audio.com/hiragana_a_meaning.mp3', NULL, NULL, 'a', 'あかい (akai) - red', NULL, 'EASY', 'hiragana,vowels,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(2, 1, NULL, 'い', 'i', 'https://audio.com/hiragana_i.mp3', 'https://audio.com/hiragana_i_meaning.mp3', NULL, NULL, 'i', 'いぬ (inu) - dog', NULL, 'EASY', 'hiragana,vowels,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(3, 1, NULL, 'う', 'u', 'https://audio.com/hiragana_u.mp3', 'https://audio.com/hiragana_u_meaning.mp3', NULL, NULL, 'u', 'うま (uma) - horse', NULL, 'EASY', 'hiragana,vowels,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(4, 1, NULL, 'え', 'e', 'https://audio.com/hiragana_e.mp3', 'https://audio.com/hiragana_e_meaning.mp3', NULL, NULL, 'e', 'えんぴつ (enpitsu) - pencil', NULL, 'EASY', 'hiragana,vowels,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(5, 1, NULL, 'お', 'o', 'https://audio.com/hiragana_o.mp3', 'https://audio.com/hiragana_o_meaning.mp3', NULL, NULL, 'o', 'おかね (okane) - money', NULL, 'EASY', 'hiragana,vowels,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(6, 1, NULL, 'か', 'ka', 'https://audio.com/hiragana_ka.mp3', 'https://audio.com/hiragana_ka_meaning.mp3', NULL, NULL, 'ka', 'かばん (kaban) - bag', NULL, 'EASY', 'hiragana,k-line,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(7, 1, NULL, 'き', 'ki', 'https://audio.com/hiragana_ki.mp3', 'https://audio.com/hiragana_ki_meaning.mp3', NULL, NULL, 'ki', 'きつね (kitsune) - fox', NULL, 'EASY', 'hiragana,k-line,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(8, 1, NULL, 'く', 'ku', 'https://audio.com/hiragana_ku.mp3', 'https://audio.com/hiragana_ku_meaning.mp3', NULL, NULL, 'ku', 'くつ (kutsu) - shoes', NULL, 'EASY', 'hiragana,k-line,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(9, 1, NULL, 'け', 'ke', 'https://audio.com/hiragana_ke.mp3', 'https://audio.com/hiragana_ke_meaning.mp3', NULL, NULL, 'ke', 'けん (ken) - sword', NULL, 'EASY', 'hiragana,k-line,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(10, 1, NULL, 'こ', 'ko', 'https://audio.com/hiragana_ko.mp3', 'https://audio.com/hiragana_ko_meaning.mp3', NULL, NULL, 'ko', 'こども (kodomo) - child', NULL, 'EASY', 'hiragana,k-line,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(11, 1, NULL, 'ア', 'a', 'https://audio.com/katakana_a.mp3', 'https://audio.com/katakana_a_meaning.mp3', NULL, NULL, 'a', 'アメリカ (amerika) - America', NULL, 'EASY', 'katakana,vowels,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(12, 1, NULL, 'イ', 'i', 'https://audio.com/katakana_i.mp3', 'https://audio.com/katakana_i_meaning.mp3', NULL, NULL, 'i', 'イギリス (igirisu) - England', NULL, 'EASY', 'katakana,vowels,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(13, 1, NULL, 'ウ', 'u', 'https://audio.com/katakana_u.mp3', 'https://audio.com/katakana_u_meaning.mp3', NULL, NULL, 'u', 'ウルトラ (urutora) - ultra', NULL, 'EASY', 'katakana,vowels,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(14, 1, NULL, 'エ', 'e', 'https://audio.com/katakana_e.mp3', 'https://audio.com/katakana_e_meaning.mp3', NULL, NULL, 'e', 'エレベーター (erebeetaa) - elevator', NULL, 'EASY', 'katakana,vowels,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(15, 1, NULL, 'オ', 'o', 'https://audio.com/katakana_o.mp3', 'https://audio.com/katakana_o_meaning.mp3', NULL, NULL, 'o', 'オレンジ (orenji) - orange', NULL, 'EASY', 'katakana,vowels,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(16, 2, NULL, 'は (wa)', 'topic particle', 'https://audio.com/particle_wa.mp3', 'https://audio.com/particle_wa_meaning.mp3', NULL, NULL, 'wa', 'わたしは がくせいです (watashi wa gakusei desu) - I am a student', NULL, 'MEDIUM', 'particles,grammar,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(17, 2, NULL, 'が (ga)', 'subject particle', 'https://audio.com/particle_ga.mp3', 'https://audio.com/particle_ga_meaning.mp3', NULL, NULL, 'ga', 'だれが きましたか (dare ga kimashita ka) - Who came?', NULL, 'MEDIUM', 'particles,grammar,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(18, 2, NULL, 'を (wo/o)', 'object particle', 'https://audio.com/particle_wo.mp3', 'https://audio.com/particle_wo_meaning.mp3', NULL, NULL, 'wo/o', 'ごはんを たべます (gohan wo tabemasu) - I eat rice', NULL, 'MEDIUM', 'particles,grammar,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(19, 2, NULL, 'に (ni)', 'direction/time particle', 'https://audio.com/particle_ni.mp3', 'https://audio.com/particle_ni_meaning.mp3', NULL, NULL, 'ni', 'がっこうに いきます (gakkou ni ikimasu) - I go to school', NULL, 'MEDIUM', 'particles,grammar,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(20, 2, NULL, 'で (de)', 'location/method particle', 'https://audio.com/particle_de.mp3', 'https://audio.com/particle_de_meaning.mp3', NULL, NULL, 'de', 'レストランで たべます (resutoran de tabemasu) - I eat at a restaurant', NULL, 'MEDIUM', 'particles,grammar,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(21, 2, NULL, 'です (desu)', 'polite copula', 'https://audio.com/desu.mp3', 'https://audio.com/desu_meaning.mp3', NULL, NULL, 'desu', 'これは ほんです (kore wa hon desu) - This is a book', NULL, 'EASY', 'copula,grammar,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(22, 2, NULL, 'ます (masu)', 'polite verb ending', 'https://audio.com/masu.mp3', 'https://audio.com/masu_meaning.mp3', NULL, NULL, 'masu', 'たべます (tabemasu) - I eat', NULL, 'EASY', 'verbs,grammar,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(23, 2, NULL, 'た (ta)', 'past tense ending', 'https://audio.com/ta.mp3', 'https://audio.com/ta_meaning.mp3', NULL, NULL, 'ta', 'たべました (tabemashita) - I ate', NULL, 'MEDIUM', 'verbs,grammar,past', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(24, 3, NULL, 'こんにちは', 'hello/good afternoon', 'https://audio.com/konnichiwa.mp3', 'https://audio.com/konnichiwa_meaning.mp3', NULL, NULL, 'konnichiwa', 'こんにちは、げんきですか (konnichiwa, genki desu ka) - Hello, how are you?', NULL, 'EASY', 'greetings,vocabulary,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(25, 3, NULL, 'ありがとう', 'thank you', 'https://audio.com/arigatou.mp3', 'https://audio.com/arigatou_meaning.mp3', NULL, NULL, 'arigatou', 'ありがとうございます (arigatou gozaimasu) - Thank you very much', NULL, 'EASY', 'greetings,vocabulary,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(26, 3, NULL, 'すみません', 'excuse me/sorry', 'https://audio.com/sumimasen.mp3', 'https://audio.com/sumimasen_meaning.mp3', NULL, NULL, 'sumimasen', 'すみません、トイレはどこですか (sumimasen, toire wa doko desu ka) - Excuse me, where is the bathroom?', NULL, 'EASY', 'greetings,vocabulary,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(27, 3, NULL, 'いえ', 'house/home', 'https://audio.com/ie.mp3', 'https://audio.com/ie_meaning.mp3', NULL, NULL, 'ie', 'わたしのいえ (watashi no ie) - my house', NULL, 'EASY', 'vocabulary,places,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(28, 3, NULL, 'がっこう', 'school', 'https://audio.com/gakkou.mp3', 'https://audio.com/gakkou_meaning.mp3', NULL, NULL, 'gakkou', 'がっこうにいきます (gakkou ni ikimasu) - I go to school', NULL, 'EASY', 'vocabulary,places,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(29, 3, NULL, 'ほん', 'book', 'https://audio.com/hon.mp3', 'https://audio.com/hon_meaning.mp3', NULL, NULL, 'hon', 'ほんをよみます (hon wo yomimasu) - I read a book', NULL, 'EASY', 'vocabulary,objects,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(30, 3, NULL, 'みず', 'water', 'https://audio.com/mizu.mp3', 'https://audio.com/mizu_meaning.mp3', NULL, NULL, 'mizu', 'みずをのみます (mizu wo nomimasu) - I drink water', NULL, 'EASY', 'vocabulary,food,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(31, 3, NULL, 'たべもの', 'food', 'https://audio.com/tabemono.mp3', 'https://audio.com/tabemono_meaning.mp3', NULL, NULL, 'tabemono', 'おいしいたべもの (oishii tabemono) - delicious food', NULL, 'EASY', 'vocabulary,food,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(32, 3, NULL, 'くるま', 'car', 'https://audio.com/kuruma.mp3', 'https://audio.com/kuruma_meaning.mp3', NULL, NULL, 'kuruma', 'くるまでいきます (kuruma de ikimasu) - I go by car', NULL, 'EASY', 'vocabulary,transportation,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(33, 3, NULL, 'でんしゃ', 'train', 'https://audio.com/densha.mp3', 'https://audio.com/densha_meaning.mp3', NULL, NULL, 'densha', 'でんしゃにのります (densha ni norimasu) - I ride the train', NULL, 'EASY', 'vocabulary,transportation,basic', 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(34, NULL, 15, 'おはよう', 'good morning', 'https://audio.com/ohayou.mp3', 'https://audio.com/ohayou_meaning.mp3', NULL, NULL, 'ohayou', 'おはようございます (ohayou gozaimasu) - Good morning', NULL, 'EASY', 'greetings,personal', 0, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(35, NULL, 15, 'こんばんは', 'good evening', 'https://audio.com/konbanwa.mp3', 'https://audio.com/konbanwa_meaning.mp3', NULL, NULL, 'konbanwa', 'こんばんは、おつかれさまです (konbanwa, otsukaresama desu) - Good evening, good work', NULL, 'EASY', 'greetings,personal', 0, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(36, NULL, 16, 'おやすみ', 'good night', 'https://audio.com/oyasumi.mp3', 'https://audio.com/oyasumi_meaning.mp3', NULL, NULL, 'oyasumi', 'おやすみなさい (oyasumi nasai) - Good night', NULL, 'EASY', 'greetings,personal', 0, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(37, NULL, 16, 'さようなら', 'goodbye', 'https://audio.com/sayounara.mp3', 'https://audio.com/sayounara_meaning.mp3', NULL, NULL, 'sayounara', 'さようなら、またあした (sayounara, mata ashita) - Goodbye, see you tomorrow', NULL, 'EASY', 'greetings,personal', 0, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(38, NULL, 17, 'はい', 'yes', 'https://audio.com/hai.mp3', 'https://audio.com/hai_meaning.mp3', NULL, NULL, 'hai', 'はい、そうです (hai, sou desu) - Yes, that\'s right', NULL, 'EASY', 'responses,personal', 0, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(39, NULL, 17, 'いいえ', 'no', 'https://audio.com/iie.mp3', 'https://audio.com/iie_meaning.mp3', NULL, NULL, 'iie', 'いいえ、ちがいます (iie, chigaimasu) - No, that\'s wrong', NULL, 'EASY', 'responses,personal', 0, '2025-10-26 17:02:58', '2025-10-26 17:02:58');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `flashcard_lessons`
--

CREATE TABLE `flashcard_lessons` (
  `flashcard_lesson_id` int(11) NOT NULL,
  `course_id` int(11) DEFAULT NULL,
  `lesson_id` int(11) DEFAULT NULL,
  `lesson_name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `lesson_order` int(11) DEFAULT 0,
  `is_active` tinyint(1) DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `flashcard_lesson_items`
--

CREATE TABLE `flashcard_lesson_items` (
  `item_id` int(11) NOT NULL,
  `flashcard_lesson_id` int(11) NOT NULL,
  `flashcard_id` int(11) NOT NULL,
  `item_order` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `lessons`
--

CREATE TABLE `lessons` (
  `lesson_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `lesson_code` varchar(50) NOT NULL,
  `lesson_name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `content` text DEFAULT NULL COMMENT 'Lesson content in HTML/Markdown',
  `video_url` varchar(500) DEFAULT NULL,
  `audio_url` varchar(500) DEFAULT NULL,
  `document_url` varchar(500) DEFAULT NULL,
  `lesson_order` int(11) NOT NULL DEFAULT 0,
  `duration_minutes` int(11) DEFAULT NULL COMMENT 'Estimated duration',
  `lesson_type` enum('VIDEO','READING','AUDIO','INTERACTIVE','FLASHCARD','TEST') DEFAULT 'READING',
  `is_preview` tinyint(1) DEFAULT 0 COMMENT 'Allow preview without enrollment',
  `is_active` tinyint(1) DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `lessons`
--

INSERT INTO `lessons` (`lesson_id`, `course_id`, `lesson_code`, `lesson_name`, `description`, `content`, `video_url`, `audio_url`, `document_url`, `lesson_order`, `duration_minutes`, `lesson_type`, `is_preview`, `is_active`, `created_at`, `updated_at`) VALUES
(1, 1, 'HIRAGANA_01', 'Introduction to Hiragana', 'Learn the basic hiragana characters and their pronunciation', '<h2>Introduction to Hiragana</h2><p>Hiragana is one of the three Japanese writing systems...</p>', 'https://youtube.com/watch?v=hiragana01', 'https://audio.com/hiragana01.mp3', 'https://docs.com/hiragana01.pdf', 1, 45, 'VIDEO', 1, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(2, 1, 'HIRAGANA_02', 'Hiragana Vowels (あいうえお)', 'Master the five basic vowels in hiragana', '<h2>Hiragana Vowels</h2><p>The five vowels are: あ (a), い (i), う (u), え (e), お (o)...</p>', 'https://youtube.com/watch?v=hiragana02', 'https://audio.com/hiragana02.mp3', 'https://docs.com/hiragana02.pdf', 2, 30, 'VIDEO', 1, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(3, 1, 'HIRAGANA_03', 'Hiragana K-line (かきくけこ)', 'Learn the K-line characters', '<h2>Hiragana K-line</h2><p>The K-line consists of: か (ka), き (ki), く (ku), け (ke), こ (ko)...</p>', 'https://youtube.com/watch?v=hiragana03', 'https://audio.com/hiragana03.mp3', 'https://docs.com/hiragana03.pdf', 3, 35, 'VIDEO', 0, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(4, 1, 'HIRAGANA_04', 'Hiragana S-line (さしすせそ)', 'Learn the S-line characters', '<h2>Hiragana S-line</h2><p>The S-line consists of: さ (sa), し (shi), す (su), せ (se), そ (so)...</p>', 'https://youtube.com/watch?v=hiragana04', 'https://audio.com/hiragana04.mp3', 'https://docs.com/hiragana04.pdf', 4, 35, 'VIDEO', 0, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(5, 1, 'KATAKANA_01', 'Introduction to Katakana', 'Learn the basic katakana characters and their usage', '<h2>Introduction to Katakana</h2><p>Katakana is used for foreign words, names, and emphasis...</p>', 'https://youtube.com/watch?v=katakana01', 'https://audio.com/katakana01.mp3', 'https://docs.com/katakana01.pdf', 5, 40, 'VIDEO', 0, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(6, 1, 'KATAKANA_02', 'Katakana Vowels (アイウエオ)', 'Master the five basic vowels in katakana', '<h2>Katakana Vowels</h2><p>The five vowels are: ア (a), イ (i), ウ (u), エ (e), オ (o)...</p>', 'https://youtube.com/watch?v=katakana02', 'https://audio.com/katakana02.mp3', 'https://docs.com/katakana02.pdf', 6, 30, 'VIDEO', 0, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(7, 2, 'GRAMMAR_01', 'Basic Sentence Structure', 'Learn the basic Japanese sentence pattern: Subject + Object + Verb', '<h2>Basic Sentence Structure</h2><p>Japanese follows a SOV (Subject-Object-Verb) pattern...</p>', 'https://youtube.com/watch?v=grammar01', 'https://audio.com/grammar01.mp3', 'https://docs.com/grammar01.pdf', 1, 50, 'VIDEO', 1, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(8, 2, 'GRAMMAR_02', 'Particles: は (wa) and が (ga)', 'Understand the difference between topic particle は and subject particle が', '<h2>Particles は and が</h2><p>は marks the topic of the sentence, while が marks the subject...</p>', 'https://youtube.com/watch?v=grammar02', 'https://audio.com/grammar02.mp3', 'https://docs.com/grammar02.pdf', 2, 45, 'VIDEO', 1, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(9, 2, 'GRAMMAR_03', 'Particle を (wo/o)', 'Learn how to use the object particle を', '<h2>Particle を</h2><p>The particle を marks the direct object of a transitive verb...</p>', 'https://youtube.com/watch?v=grammar03', 'https://audio.com/grammar03.mp3', 'https://docs.com/grammar03.pdf', 3, 40, 'VIDEO', 0, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(10, 2, 'GRAMMAR_04', 'Present Tense Verbs', 'Learn basic present tense verb conjugations', '<h2>Present Tense Verbs</h2><p>Japanese verbs have different forms for present tense...</p>', 'https://youtube.com/watch?v=grammar04', 'https://audio.com/grammar04.mp3', 'https://docs.com/grammar04.pdf', 4, 55, 'VIDEO', 0, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(11, 2, 'GRAMMAR_05', 'Past Tense Verbs', 'Learn how to conjugate verbs to past tense', '<h2>Past Tense Verbs</h2><p>Past tense is formed by changing the verb ending...</p>', 'https://youtube.com/watch?v=grammar05', 'https://audio.com/grammar05.mp3', 'https://docs.com/grammar05.pdf', 5, 50, 'VIDEO', 0, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(12, 4, 'CONV_01', 'Greetings and Introductions', 'Learn basic Japanese greetings and how to introduce yourself', '<h2>Greetings and Introductions</h2><p>Common greetings include: おはよう (ohayou), こんにちは (konnichiwa)...</p>', 'https://youtube.com/watch?v=conv01', 'https://audio.com/conv01.mp3', 'https://docs.com/conv01.pdf', 1, 40, 'VIDEO', 1, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(13, 4, 'CONV_02', 'Asking for Directions', 'Learn how to ask for and give directions in Japanese', '<h2>Asking for Directions</h2><p>Useful phrases: すみません (sumimasen), どこですか (doko desu ka)...</p>', 'https://youtube.com/watch?v=conv02', 'https://audio.com/conv02.mp3', 'https://docs.com/conv02.pdf', 2, 45, 'VIDEO', 1, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(14, 4, 'CONV_03', 'Shopping Conversations', 'Learn vocabulary and phrases for shopping', '<h2>Shopping Conversations</h2><p>Key phrases: いくらですか (ikura desu ka), これをください (kore wo kudasai)...</p>', 'https://youtube.com/watch?v=conv03', 'https://audio.com/conv03.mp3', 'https://docs.com/conv03.pdf', 3, 50, 'VIDEO', 0, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(15, 4, 'CONV_04', 'Ordering Food', 'Learn how to order food at restaurants', '<h2>Ordering Food</h2><p>Restaurant phrases: メニューをください (menu wo kudasai), おすすめは何ですか (osusume wa nan desu ka)...</p>', 'https://youtube.com/watch?v=conv04', 'https://audio.com/conv04.mp3', 'https://docs.com/conv04.pdf', 4, 45, 'VIDEO', 0, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(16, 13, 'JLPTN5_01', 'JLPT N5 Vocabulary - Part 1', 'Essential vocabulary for JLPT N5 level', '<h2>JLPT N5 Vocabulary</h2><p>Learn the most important words for N5 level...</p>', 'https://youtube.com/watch?v=n5vocab01', 'https://audio.com/n5vocab01.mp3', 'https://docs.com/n5vocab01.pdf', 1, 60, 'VIDEO', 1, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(17, 13, 'JLPTN5_02', 'JLPT N5 Grammar - Part 1', 'Essential grammar patterns for JLPT N5', '<h2>JLPT N5 Grammar</h2><p>Master the grammar patterns required for N5...</p>', 'https://youtube.com/watch?v=n5grammar01', 'https://audio.com/n5grammar01.mp3', 'https://docs.com/n5grammar01.pdf', 2, 55, 'VIDEO', 1, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(18, 13, 'JLPTN5_03', 'JLPT N5 Practice Test 1', 'Practice test with N5 level questions', '<h2>Practice Test 1</h2><p>Test your knowledge with this practice exam...</p>', NULL, NULL, 'https://docs.com/n5test01.pdf', 3, 90, 'TEST', 0, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(19, 13, 'JLPTN5_04', 'JLPT N5 Listening Practice', 'Practice listening comprehension for N5', '<h2>Listening Practice</h2><p>Improve your listening skills with N5 level audio...</p>', 'https://youtube.com/watch?v=n5listening01', 'https://audio.com/n5listening01.mp3', 'https://docs.com/n5listening01.pdf', 4, 45, 'AUDIO', 0, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `lesson_comments`
--

CREATE TABLE `lesson_comments` (
  `comment_id` int(11) NOT NULL,
  `lesson_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `parent_comment_id` int(11) DEFAULT NULL COMMENT 'For nested replies',
  `comment_text` text NOT NULL,
  `is_approved` tinyint(1) DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `lesson_progress`
--

CREATE TABLE `lesson_progress` (
  `progress_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `lesson_id` int(11) NOT NULL,
  `status` enum('NOT_STARTED','IN_PROGRESS','COMPLETED') DEFAULT 'NOT_STARTED',
  `progress_percentage` decimal(5,2) DEFAULT 0.00,
  `time_spent_seconds` int(11) DEFAULT 0,
  `started_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `completed_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_accessed_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `lesson_progress`
--

INSERT INTO `lesson_progress` (`progress_id`, `user_id`, `lesson_id`, `status`, `progress_percentage`, `time_spent_seconds`, `started_at`, `completed_at`, `last_accessed_at`) VALUES
(1, 15, 1, 'COMPLETED', 100.00, 2700, '2024-04-01 02:00:00', '2024-04-01 02:45:00', '2024-04-01 02:45:00'),
(2, 15, 2, 'COMPLETED', 100.00, 1800, '2024-04-03 02:00:00', '2024-04-03 02:30:00', '2024-04-03 02:30:00'),
(3, 15, 3, 'COMPLETED', 100.00, 2100, '2024-04-05 02:00:00', '2024-04-05 02:35:00', '2024-04-05 02:35:00'),
(4, 15, 4, 'IN_PROGRESS', 60.00, 1200, '2024-04-08 02:00:00', '2025-10-26 17:02:58', '2024-04-08 02:20:00'),
(5, 16, 1, 'COMPLETED', 100.00, 2400, '2024-04-01 02:00:00', '2024-04-01 02:40:00', '2024-04-01 02:40:00'),
(6, 16, 2, 'COMPLETED', 100.00, 1500, '2024-04-03 02:00:00', '2024-04-03 02:25:00', '2024-04-03 02:25:00'),
(7, 16, 3, 'IN_PROGRESS', 40.00, 800, '2024-04-05 02:00:00', '2025-10-26 17:02:58', '2024-04-05 02:13:00'),
(8, 17, 1, 'COMPLETED', 100.00, 3000, '2024-04-01 02:00:00', '2024-04-01 02:50:00', '2024-04-01 02:50:00'),
(9, 17, 2, 'COMPLETED', 100.00, 2100, '2024-04-03 02:00:00', '2024-04-03 02:35:00', '2024-04-03 02:35:00'),
(10, 17, 3, 'COMPLETED', 100.00, 2400, '2024-04-05 02:00:00', '2024-04-05 02:40:00', '2024-04-05 02:40:00'),
(11, 17, 4, 'COMPLETED', 100.00, 1800, '2024-04-08 02:00:00', '2024-04-08 02:30:00', '2024-04-08 02:30:00'),
(12, 17, 5, 'IN_PROGRESS', 30.00, 600, '2024-04-10 02:00:00', '2025-10-26 17:02:58', '2024-04-10 02:10:00');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `notifications`
--

CREATE TABLE `notifications` (
  `notification_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `message` text NOT NULL,
  `notification_type` varchar(50) DEFAULT NULL COMMENT 'ENROLLMENT, TEST_AVAILABLE, ACHIEVEMENT, SYSTEM, etc.',
  `related_entity_type` varchar(50) DEFAULT NULL COMMENT 'COURSE, CLASS, TEST, LESSON',
  `related_entity_id` int(11) DEFAULT NULL,
  `action_url` varchar(500) DEFAULT NULL,
  `is_read` tinyint(1) DEFAULT 0,
  `read_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `notifications`
--

INSERT INTO `notifications` (`notification_id`, `user_id`, `title`, `message`, `notification_type`, `related_entity_type`, `related_entity_id`, `action_url`, `is_read`, `read_at`, `created_at`) VALUES
(1, 15, 'New Lesson Available', 'A new lesson \"Hiragana S-line\" is now available in your Japanese for Beginners course.', 'LESSON_AVAILABLE', 'LESSON', 4, '/student/lessons/4', 0, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(2, 16, 'Test Results Available', 'Your results for \"Hiragana Basics Quiz\" are now available. You scored 92%!', 'TEST_RESULT', 'TEST', 1, '/student/tests/results/1', 0, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(3, 17, 'Class Session Reminder', 'Reminder: Your next class session \"Hiragana S-line\" is scheduled for tomorrow at 9:00 AM.', 'CLASS_REMINDER', 'CLASS_SESSION', 4, '/student/classes/sessions/4', 0, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(4, 18, 'Course Progress Update', 'Great job! You have completed 50% of your JLPT N5 Preparation course.', 'PROGRESS_UPDATE', 'COURSE', 13, '/student/courses/13', 0, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(5, 19, 'Flashcard Review Due', 'You have 15 flashcards due for review. Keep up your study streak!', 'FLASHCARD_REVIEW', 'FLASHCARD', NULL, '/student/flashcards/review', 0, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(6, 20, 'New Course Available', 'A new course \"Japanese Numbers and Time\" is now available for enrollment.', 'COURSE_AVAILABLE', 'COURSE', 5, '/course/5', 0, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(7, 21, 'Class Assignment', 'You have been assigned to \"Japanese Beginners - Evening Class\" by your manager.', 'CLASS_ASSIGNMENT', 'CLASS', 2, '/student/classes/2', 0, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(8, 22, 'Payment Confirmation', 'Your payment for JLPT N5 Preparation course has been confirmed. Thank you!', 'PAYMENT_CONFIRMATION', 'COURSE', 13, '/student/courses/13', 0, '2025-10-26 17:02:58', '2025-10-26 17:02:58');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `payment_transactions`
--

CREATE TABLE `payment_transactions` (
  `transaction_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `course_id` int(11) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `transaction_code` varchar(100) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `currency` varchar(10) DEFAULT 'VND',
  `payment_method` enum('CREDIT_CARD','BANK_TRANSFER','E_WALLET','CASH','OTHER') NOT NULL,
  `payment_gateway` varchar(50) DEFAULT NULL COMMENT 'VNPay, Momo, etc.',
  `gateway_transaction_id` varchar(255) DEFAULT NULL,
  `status` enum('PENDING','COMPLETED','FAILED','REFUNDED','CANCELLED') DEFAULT 'PENDING',
  `payment_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `description` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `questions`
--

CREATE TABLE `questions` (
  `question_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `question_type_id` int(11) NOT NULL,
  `question_text` text NOT NULL,
  `question_image_url` varchar(500) DEFAULT NULL,
  `question_audio_url` varchar(500) DEFAULT NULL,
  `points` decimal(5,2) DEFAULT 1.00,
  `difficulty` enum('EASY','MEDIUM','HARD') DEFAULT 'MEDIUM',
  `explanation` text DEFAULT NULL COMMENT 'Answer explanation',
  `tags` varchar(500) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT 1,
  `created_by` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `questions`
--

INSERT INTO `questions` (`question_id`, `course_id`, `question_type_id`, `question_text`, `question_image_url`, `question_audio_url`, `points`, `difficulty`, `explanation`, `tags`, `is_active`, `created_by`, `created_at`, `updated_at`) VALUES
(1, 1, 1, 'What is the correct pronunciation of あ?', NULL, 'https://audio.com/question_hiragana_a.mp3', 1.00, 'EASY', 'あ is pronounced as \"a\" in Japanese.', 'hiragana,pronunciation', 1, 6, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(2, 1, 1, 'Which hiragana character represents the sound \"ki\"?', NULL, NULL, 1.00, 'EASY', 'き represents the sound \"ki\".', 'hiragana,pronunciation', 1, 6, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(3, 1, 1, 'What is the katakana equivalent of う?', NULL, NULL, 1.00, 'MEDIUM', 'ウ is the katakana equivalent of う.', 'katakana,hiragana,equivalents', 1, 6, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(4, 1, 2, 'Which of the following are hiragana vowels?', NULL, NULL, 2.00, 'EASY', 'The hiragana vowels are あ, い, う, え, お.', 'hiragana,vowels', 1, 6, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(5, 1, 3, 'あ is pronounced as \"a\".', NULL, NULL, 1.00, 'EASY', 'Correct! あ is indeed pronounced as \"a\".', 'hiragana,pronunciation', 1, 6, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(6, 2, 1, 'Which particle marks the topic of a sentence?', NULL, NULL, 1.00, 'EASY', 'は (wa) marks the topic of a sentence.', 'particles,grammar', 1, 6, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(7, 2, 1, 'What is the correct particle for \"I eat rice\"?', NULL, NULL, 1.00, 'MEDIUM', 'ごはんをたべます uses を (wo/o) to mark the object.', 'particles,grammar', 1, 6, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(8, 2, 3, 'は (wa) is used to mark the subject of a sentence.', NULL, NULL, 1.00, 'MEDIUM', 'False. は marks the topic, not the subject. が marks the subject.', 'particles,grammar', 1, 6, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(9, 2, 4, 'Complete: わたし___ がくせいです', NULL, NULL, 1.00, 'EASY', 'わたしは がくせいです - I am a student.', 'particles,grammar', 1, 6, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(10, 2, 1, 'Which particle indicates direction or destination?', NULL, NULL, 1.00, 'MEDIUM', 'に (ni) indicates direction or destination.', 'particles,grammar', 1, 6, '2025-10-26 17:02:58', '2025-10-26 17:02:58');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `question_options`
--

CREATE TABLE `question_options` (
  `option_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `option_text` text NOT NULL,
  `option_image_url` varchar(500) DEFAULT NULL,
  `is_correct` tinyint(1) DEFAULT 0,
  `option_order` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `question_options`
--

INSERT INTO `question_options` (`option_id`, `question_id`, `option_text`, `option_image_url`, `is_correct`, `option_order`) VALUES
(1, 1, 'a', NULL, 1, 1),
(2, 1, 'i', NULL, 0, 2),
(3, 1, 'u', NULL, 0, 3),
(4, 1, 'e', NULL, 0, 4),
(5, 2, 'か', NULL, 0, 1),
(6, 2, 'き', NULL, 1, 2),
(7, 2, 'く', NULL, 0, 3),
(8, 2, 'け', NULL, 0, 4),
(9, 3, 'ア', NULL, 0, 1),
(10, 3, 'イ', NULL, 0, 2),
(11, 3, 'ウ', NULL, 1, 3),
(12, 3, 'エ', NULL, 0, 4),
(13, 4, 'あ', NULL, 1, 1),
(14, 4, 'い', NULL, 1, 2),
(15, 4, 'う', NULL, 1, 3),
(16, 4, 'え', NULL, 1, 4),
(17, 4, 'お', NULL, 1, 5),
(18, 4, 'か', NULL, 0, 6),
(19, 4, 'き', NULL, 0, 7),
(20, 6, 'は (wa)', NULL, 1, 1),
(21, 6, 'が (ga)', NULL, 0, 2),
(22, 6, 'を (wo)', NULL, 0, 3),
(23, 6, 'に (ni)', NULL, 0, 4),
(24, 7, 'ごはんはたべます', NULL, 0, 1),
(25, 7, 'ごはんをたべます', NULL, 1, 2),
(26, 7, 'ごはんがたべます', NULL, 0, 3),
(27, 7, 'ごはんにたべます', NULL, 0, 4),
(28, 10, 'は (wa)', NULL, 0, 1),
(29, 10, 'が (ga)', NULL, 0, 2),
(30, 10, 'を (wo)', NULL, 0, 3),
(31, 10, 'に (ni)', NULL, 1, 4);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `question_types`
--

CREATE TABLE `question_types` (
  `type_id` int(11) NOT NULL,
  `type_name` varchar(50) NOT NULL COMMENT 'MULTIPLE_CHOICE, TRUE_FALSE, FILL_BLANK, MATCHING, ESSAY',
  `description` text DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `question_types`
--

INSERT INTO `question_types` (`type_id`, `type_name`, `description`, `is_active`) VALUES
(1, 'MULTIPLE_CHOICE', 'Single answer from multiple options', 1),
(2, 'MULTIPLE_SELECT', 'Multiple correct answers', 1),
(3, 'TRUE_FALSE', 'True or False question', 1),
(4, 'FILL_BLANK', 'Fill in the blank', 1),
(5, 'MATCHING', 'Match pairs', 1),
(6, 'ESSAY', 'Long text answer', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL,
  `role_name` varchar(50) NOT NULL COMMENT 'GUEST, USER, STUDENT, TEACHER, EXPERT, MANAGER, ADMIN',
  `description` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `roles`
--

INSERT INTO `roles` (`role_id`, `role_name`, `description`, `created_at`, `updated_at`) VALUES
(1, 'GUEST', 'Non-registered user with limited access', '2025-10-26 17:02:40', '2025-10-26 17:02:40'),
(2, 'USER', 'Basic registered user', '2025-10-26 17:02:40', '2025-10-26 17:02:40'),
(3, 'STUDENT', 'Enrolled student with learning access', '2025-10-26 17:02:40', '2025-10-26 17:02:40'),
(4, 'TEACHER', 'Instructor managing classes', '2025-10-26 17:02:40', '2025-10-26 17:02:40'),
(5, 'EXPERT', 'Content creator managing courses', '2025-10-26 17:02:40', '2025-10-26 17:02:40'),
(6, 'MANAGER', 'Supervisor managing teachers and classes', '2025-10-26 17:02:40', '2025-10-26 17:02:40'),
(7, 'ADMIN', 'System administrator with full access', '2025-10-26 17:02:40', '2025-10-26 17:02:40');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `session_attendance`
--

CREATE TABLE `session_attendance` (
  `attendance_id` int(11) NOT NULL,
  `session_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `status` enum('PRESENT','ABSENT','LATE','EXCUSED') DEFAULT 'ABSENT',
  `check_in_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `note` text DEFAULT NULL,
  `marked_by` int(11) DEFAULT NULL COMMENT 'Teacher who marked attendance',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `social_auth_accounts`
--

CREATE TABLE `social_auth_accounts` (
  `social_auth_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `provider` enum('GOOGLE','FACEBOOK','GITHUB','APPLE') NOT NULL,
  `provider_user_id` varchar(255) NOT NULL COMMENT 'User ID from OAuth provider',
  `provider_username` varchar(255) DEFAULT NULL COMMENT 'Username from provider',
  `provider_email` varchar(255) DEFAULT NULL COMMENT 'Email from provider',
  `provider_avatar_url` varchar(500) DEFAULT NULL COMMENT 'Avatar URL from provider',
  `access_token` text DEFAULT NULL COMMENT 'OAuth access token (encrypted)',
  `refresh_token` text DEFAULT NULL COMMENT 'OAuth refresh token (encrypted)',
  `token_expires_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT 'Access token expiration',
  `provider_data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'Additional provider data' CHECK (json_valid(`provider_data`)),
  `linked_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_used_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_active` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `study_streaks`
--

CREATE TABLE `study_streaks` (
  `streak_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `current_streak` int(11) DEFAULT 0,
  `longest_streak` int(11) DEFAULT 0,
  `last_study_date` date DEFAULT NULL,
  `total_study_days` int(11) DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `study_streaks`
--

INSERT INTO `study_streaks` (`streak_id`, `user_id`, `current_streak`, `longest_streak`, `last_study_date`, `total_study_days`, `created_at`, `updated_at`) VALUES
(1, 15, 7, 12, '2024-04-08', 25, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(2, 16, 3, 8, '2024-04-08', 15, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(3, 17, 15, 20, '2024-04-08', 35, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(4, 18, 5, 10, '2024-04-08', 18, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(5, 19, 2, 6, '2024-04-08', 12, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(6, 20, 8, 14, '2024-04-08', 22, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(7, 21, 12, 18, '2024-04-08', 28, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(8, 22, 4, 9, '2024-04-08', 16, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(9, 23, 6, 11, '2024-04-08', 20, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(10, 24, 1, 5, '2024-04-08', 8, '2025-10-26 17:02:58', '2025-10-26 17:02:58');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `system_settings`
--

CREATE TABLE `system_settings` (
  `setting_id` int(11) NOT NULL,
  `setting_key` varchar(100) NOT NULL,
  `setting_value` text DEFAULT NULL,
  `setting_type` varchar(50) DEFAULT NULL COMMENT 'STRING, NUMBER, BOOLEAN, JSON',
  `description` text DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `system_settings`
--

INSERT INTO `system_settings` (`setting_id`, `setting_key`, `setting_value`, `setting_type`, `description`, `is_active`, `created_at`, `updated_at`) VALUES
(1, 'site_name', 'AuroraLang', 'STRING', 'Website name', 1, '2025-10-26 17:02:40', '2025-10-26 17:02:40'),
(2, 'default_language', 'Japanese', 'STRING', 'Default learning language', 1, '2025-10-26 17:02:40', '2025-10-26 17:02:40'),
(3, 'max_login_attempts', '5', 'NUMBER', 'Maximum login attempts before lock', 1, '2025-10-26 17:02:40', '2025-10-26 17:02:40'),
(4, 'session_timeout_minutes', '30', 'NUMBER', 'Session timeout in minutes', 1, '2025-10-26 17:02:40', '2025-10-26 17:02:40'),
(5, 'enable_registration', 'true', 'BOOLEAN', 'Enable user registration', 1, '2025-10-26 17:02:40', '2025-10-26 17:02:40'),
(6, 'enable_payment', 'false', 'BOOLEAN', 'Enable online payment (Phase 2)', 1, '2025-10-26 17:02:40', '2025-10-26 17:02:40');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tests`
--

CREATE TABLE `tests` (
  `test_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `test_code` varchar(50) NOT NULL,
  `test_name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `test_type` enum('QUIZ','MIDTERM','FINAL','PRACTICE') DEFAULT 'QUIZ',
  `duration_minutes` int(11) NOT NULL DEFAULT 60,
  `passing_score` decimal(5,2) DEFAULT 70.00,
  `total_points` decimal(8,2) DEFAULT 100.00,
  `max_attempts` int(11) DEFAULT 0 COMMENT '0 = unlimited',
  `shuffle_questions` tinyint(1) DEFAULT 0,
  `shuffle_options` tinyint(1) DEFAULT 0,
  `show_correct_answers` tinyint(1) DEFAULT 1,
  `available_from` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `available_until` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_active` tinyint(1) DEFAULT 1,
  `created_by` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `tests`
--

INSERT INTO `tests` (`test_id`, `course_id`, `test_code`, `test_name`, `description`, `test_type`, `duration_minutes`, `passing_score`, `total_points`, `max_attempts`, `shuffle_questions`, `shuffle_options`, `show_correct_answers`, `available_from`, `available_until`, `is_active`, `created_by`, `created_at`, `updated_at`) VALUES
(1, 1, 'HIRAGANA_QUIZ_01', 'Hiragana Basics Quiz', 'Test your knowledge of basic hiragana characters', 'QUIZ', 30, 70.00, 100.00, 3, 1, 1, 1, '2024-01-14 17:00:00', '2024-12-31 16:59:59', 1, 6, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(2, 1, 'KATAKANA_QUIZ_01', 'Katakana Basics Quiz', 'Test your knowledge of basic katakana characters', 'QUIZ', 30, 70.00, 100.00, 3, 1, 1, 1, '2024-01-19 17:00:00', '2024-12-31 16:59:59', 1, 6, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(3, 2, 'GRAMMAR_QUIZ_01', 'Basic Grammar Quiz', 'Test your understanding of basic Japanese grammar', 'QUIZ', 45, 75.00, 100.00, 2, 1, 1, 1, '2024-01-24 17:00:00', '2024-12-31 16:59:59', 1, 6, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(4, 13, 'JLPTN5_PRACTICE_01', 'JLPT N5 Practice Test 1', 'Full practice test for JLPT N5 level', 'PRACTICE', 90, 60.00, 180.00, 0, 1, 1, 0, '2024-01-31 17:00:00', '2024-12-31 16:59:59', 1, 6, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(5, 13, 'JLPTN5_MIDTERM', 'JLPT N5 Midterm Test', 'Midterm assessment for JLPT N5 preparation', 'MIDTERM', 60, 80.00, 120.00, 1, 0, 0, 1, '2024-02-29 17:00:00', '2024-03-31 16:59:59', 1, 6, '2025-10-26 17:02:58', '2025-10-26 17:02:58');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `test_questions`
--

CREATE TABLE `test_questions` (
  `test_question_id` int(11) NOT NULL,
  `test_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `question_order` int(11) DEFAULT 0,
  `points_override` decimal(5,2) DEFAULT NULL COMMENT 'Override question default points'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `test_questions`
--

INSERT INTO `test_questions` (`test_question_id`, `test_id`, `question_id`, `question_order`, `points_override`) VALUES
(1, 1, 1, 1, 1.00),
(2, 1, 2, 2, 1.00),
(3, 1, 3, 3, 1.00),
(4, 1, 4, 4, 2.00),
(5, 1, 5, 5, 1.00),
(6, 3, 6, 1, 1.00),
(7, 3, 7, 2, 1.00),
(8, 3, 8, 3, 1.00),
(9, 3, 9, 4, 1.00),
(10, 3, 10, 5, 1.00);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password_hash` varchar(255) DEFAULT NULL COMMENT 'NULL for OAuth users',
  `full_name` varchar(255) NOT NULL,
  `avatar_url` varchar(500) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `gender` enum('MALE','FEMALE','OTHER') DEFAULT NULL,
  `address` text DEFAULT NULL,
  `auth_provider` enum('LOCAL','GOOGLE','FACEBOOK') DEFAULT 'LOCAL' COMMENT 'Authentication provider',
  `is_active` tinyint(1) DEFAULT 1,
  `is_email_verified` tinyint(1) DEFAULT 0,
  `email_verification_token` varchar(255) DEFAULT NULL,
  `password_reset_token` varchar(255) DEFAULT NULL,
  `password_reset_expiry` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `last_login` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `login_attempts` int(11) DEFAULT 0,
  `locked_until` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`user_id`, `email`, `username`, `password_hash`, `full_name`, `avatar_url`, `phone`, `date_of_birth`, `gender`, `address`, `auth_provider`, `is_active`, `is_email_verified`, `email_verification_token`, `password_reset_token`, `password_reset_expiry`, `last_login`, `login_attempts`, `locked_until`, `created_at`, `updated_at`) VALUES
(1, 'admin@auroralang.com', 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'System Administrator', '/images/avatars/admin.jpg', '0123456789', '1990-01-01', 'MALE', 'Hanoi, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(2, 'admin2@auroralang.com', 'admin2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Admin User 2', '/images/avatars/admin2.jpg', '0123456790', '1985-05-15', 'FEMALE', 'Ho Chi Minh City, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(3, 'manager1@auroralang.com', 'manager1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Nguyen Van Manager', '/images/avatars/manager1.jpg', '0123456791', '1988-03-20', 'MALE', 'Da Nang, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(4, 'manager2@auroralang.com', 'manager2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Tran Thi Manager', '/images/avatars/manager2.jpg', '0123456792', '1992-07-10', 'FEMALE', 'Hue, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(5, 'manager3@auroralang.com', 'manager3', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Le Van Manager', '/images/avatars/manager3.jpg', '0123456793', '1987-11-25', 'MALE', 'Can Tho, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(6, 'expert1@auroralang.com', 'expert1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Yamada Sensei', '/images/avatars/expert1.jpg', '0123456794', '1980-02-14', 'MALE', 'Tokyo, Japan', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(7, 'expert2@auroralang.com', 'expert2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Sato Sensei', '/images/avatars/expert2.jpg', '0123456795', '1983-09-08', 'FEMALE', 'Osaka, Japan', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(8, 'expert3@auroralang.com', 'expert3', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Tanaka Sensei', '/images/avatars/expert3.jpg', '0123456796', '1978-12-03', 'MALE', 'Kyoto, Japan', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(9, 'expert4@auroralang.com', 'expert4', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Nakamura Sensei', '/images/avatars/expert4.jpg', '0123456797', '1985-06-18', 'FEMALE', 'Yokohama, Japan', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(10, 'teacher1@auroralang.com', 'teacher1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Nguyen Thi Teacher', '/images/avatars/teacher1.jpg', '0123456798', '1990-04-12', 'FEMALE', 'Hanoi, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(11, 'teacher2@auroralang.com', 'teacher2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Tran Van Teacher', '/images/avatars/teacher2.jpg', '0123456799', '1988-08-30', 'MALE', 'Ho Chi Minh City, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(12, 'teacher3@auroralang.com', 'teacher3', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Le Thi Teacher', '/images/avatars/teacher3.jpg', '0123456800', '1992-01-22', 'FEMALE', 'Da Nang, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(13, 'teacher4@auroralang.com', 'teacher4', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Pham Van Teacher', '/images/avatars/teacher4.jpg', '0123456801', '1987-10-15', 'MALE', 'Hue, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(14, 'teacher5@auroralang.com', 'teacher5', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Hoang Thi Teacher', '/images/avatars/teacher5.jpg', '0123456802', '1991-03-07', 'FEMALE', 'Can Tho, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(15, 'student1@auroralang.com', 'student1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Nguyen Van Student', '/images/avatars/student1.jpg', '0123456803', '2000-05-20', 'MALE', 'Hanoi, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(16, 'student2@auroralang.com', 'student2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Tran Thi Student', '/images/avatars/student2.jpg', '0123456804', '2001-08-15', 'FEMALE', 'Ho Chi Minh City, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(17, 'student3@auroralang.com', 'student3', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Le Van Student', '/images/avatars/student3.jpg', '0123456805', '1999-12-10', 'MALE', 'Da Nang, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(18, 'student4@auroralang.com', 'student4', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Pham Thi Student', '/images/avatars/student4.jpg', '0123456806', '2002-02-28', 'FEMALE', 'Hue, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(19, 'student5@auroralang.com', 'student5', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Hoang Van Student', '/images/avatars/student5.jpg', '0123456807', '2000-07-03', 'MALE', 'Can Tho, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(20, 'student6@auroralang.com', 'student6', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Vu Thi Student', '/images/avatars/student6.jpg', '0123456808', '2001-11-18', 'FEMALE', 'Hanoi, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(21, 'student7@auroralang.com', 'student7', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Dang Van Student', '/images/avatars/student7.jpg', '0123456809', '1999-04-25', 'MALE', 'Ho Chi Minh City, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(22, 'student8@auroralang.com', 'student8', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Bui Thi Student', '/images/avatars/student8.jpg', '0123456810', '2002-09-12', 'FEMALE', 'Da Nang, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(23, 'student9@auroralang.com', 'student9', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Ngo Van Student', '/images/avatars/student9.jpg', '0123456811', '2000-01-30', 'MALE', 'Hue, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11'),
(24, 'student10@auroralang.com', 'student10', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Do Thi Student', '/images/avatars/student10.jpg', '0123456812', '2001-06-08', 'FEMALE', 'Can Tho, Vietnam', 'LOCAL', 1, 1, NULL, NULL, '2025-10-26 17:03:11', '2025-10-26 17:03:11', 0, '2025-10-26 17:03:11', '2025-10-26 17:02:58', '2025-10-26 17:03:11');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_activity_logs`
--

CREATE TABLE `user_activity_logs` (
  `log_id` bigint(20) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `activity_type` varchar(100) NOT NULL COMMENT 'LOGIN, LOGOUT, PASSWORD_CHANGE, PROFILE_UPDATE, etc.',
  `description` text DEFAULT NULL,
  `ip_address` varchar(45) DEFAULT NULL,
  `user_agent` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_answers`
--

CREATE TABLE `user_answers` (
  `answer_id` bigint(20) NOT NULL,
  `attempt_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `selected_option_id` int(11) DEFAULT NULL COMMENT 'For multiple choice',
  `answer_text` text DEFAULT NULL COMMENT 'For text answers',
  `is_correct` tinyint(1) DEFAULT NULL,
  `points_earned` decimal(5,2) DEFAULT NULL,
  `answered_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `user_answers`
--

INSERT INTO `user_answers` (`answer_id`, `attempt_id`, `question_id`, `selected_option_id`, `answer_text`, `is_correct`, `points_earned`, `answered_at`) VALUES
(1, 1, 1, 1, NULL, 1, 1.00, '2025-10-26 17:02:58'),
(2, 1, 2, 2, NULL, 1, 1.00, '2025-10-26 17:02:58'),
(3, 1, 3, 3, NULL, 1, 1.00, '2025-10-26 17:02:58'),
(4, 1, 4, 1, NULL, 1, 2.00, '2025-10-26 17:02:58'),
(5, 1, 5, 1, NULL, 1, 1.00, '2025-10-26 17:02:58'),
(6, 3, 1, 1, NULL, 1, 1.00, '2025-10-26 17:02:58'),
(7, 3, 2, 2, NULL, 1, 1.00, '2025-10-26 17:02:58'),
(8, 3, 3, 3, NULL, 1, 1.00, '2025-10-26 17:02:58'),
(9, 3, 4, 1, NULL, 1, 2.00, '2025-10-26 17:02:58'),
(10, 3, 5, 1, NULL, 1, 1.00, '2025-10-26 17:02:58'),
(11, 4, 1, 1, NULL, 1, 1.00, '2025-10-26 17:02:58'),
(12, 4, 2, 2, NULL, 1, 1.00, '2025-10-26 17:02:58'),
(13, 4, 3, 3, NULL, 1, 1.00, '2025-10-26 17:02:58'),
(14, 4, 4, 1, NULL, 1, 2.00, '2025-10-26 17:02:58'),
(15, 4, 5, 1, NULL, 1, 1.00, '2025-10-26 17:02:58');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_flashcard_progress`
--

CREATE TABLE `user_flashcard_progress` (
  `progress_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `flashcard_id` int(11) NOT NULL,
  `mastery_level` int(11) DEFAULT 0 COMMENT '0-5 scale',
  `correct_count` int(11) DEFAULT 0,
  `incorrect_count` int(11) DEFAULT 0,
  `last_reviewed_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `next_review_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Spaced repetition scheduling',
  `ease_factor` decimal(3,2) DEFAULT 2.50 COMMENT 'SM-2 algorithm',
  `interval_days` int(11) DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `user_flashcard_progress`
--

INSERT INTO `user_flashcard_progress` (`progress_id`, `user_id`, `flashcard_id`, `mastery_level`, `correct_count`, `incorrect_count`, `last_reviewed_at`, `next_review_at`, `ease_factor`, `interval_days`, `created_at`, `updated_at`) VALUES
(1, 15, 1, 3, 5, 1, '2024-04-08 03:00:00', '2024-04-10 03:00:00', 2.50, 2, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(2, 15, 2, 4, 8, 0, '2024-04-08 03:05:00', '2024-04-12 03:05:00', 2.60, 4, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(3, 15, 3, 2, 3, 2, '2024-04-08 03:10:00', '2024-04-09 03:10:00', 2.30, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(4, 15, 4, 5, 10, 0, '2024-04-08 03:15:00', '2024-04-20 03:15:00', 2.80, 12, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(5, 16, 1, 2, 2, 1, '2024-04-08 03:20:00', '2024-04-09 03:20:00', 2.20, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(6, 16, 2, 3, 4, 1, '2024-04-08 03:25:00', '2024-04-11 03:25:00', 2.40, 3, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(7, 16, 5, 1, 1, 2, '2024-04-08 03:30:00', '2024-04-09 03:30:00', 2.10, 1, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(8, 17, 1, 5, 12, 0, '2024-04-08 03:35:00', '2024-04-25 03:35:00', 2.90, 17, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(9, 17, 2, 4, 7, 1, '2024-04-08 03:40:00', '2024-04-15 03:40:00', 2.70, 7, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(10, 17, 3, 3, 5, 1, '2024-04-08 03:45:00', '2024-04-12 03:45:00', 2.50, 4, '2025-10-26 17:02:58', '2025-10-26 17:02:58'),
(11, 17, 4, 4, 6, 0, '2024-04-08 03:50:00', '2024-04-16 03:50:00', 2.60, 8, '2025-10-26 17:02:58', '2025-10-26 17:02:58');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_roles`
--

CREATE TABLE `user_roles` (
  `user_role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `assigned_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `assigned_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `user_roles`
--

INSERT INTO `user_roles` (`user_role_id`, `user_id`, `role_id`, `assigned_at`, `assigned_by`) VALUES
(1, 1, 7, '2025-10-26 17:02:58', 1),
(2, 2, 7, '2025-10-26 17:02:58', 1),
(3, 3, 6, '2025-10-26 17:02:58', 1),
(4, 4, 6, '2025-10-26 17:02:58', 1),
(5, 5, 6, '2025-10-26 17:02:58', 1),
(6, 6, 5, '2025-10-26 17:02:58', 1),
(7, 7, 5, '2025-10-26 17:02:58', 1),
(8, 8, 5, '2025-10-26 17:02:58', 1),
(9, 9, 5, '2025-10-26 17:02:58', 1),
(10, 10, 4, '2025-10-26 17:02:58', 3),
(11, 11, 4, '2025-10-26 17:02:58', 3),
(12, 12, 4, '2025-10-26 17:02:58', 4),
(13, 13, 4, '2025-10-26 17:02:58', 4),
(14, 14, 4, '2025-10-26 17:02:58', 5),
(15, 15, 3, '2025-10-26 17:02:58', 1),
(16, 16, 3, '2025-10-26 17:02:58', 1),
(17, 17, 3, '2025-10-26 17:02:58', 1),
(18, 18, 3, '2025-10-26 17:02:58', 1),
(19, 19, 3, '2025-10-26 17:02:58', 1),
(20, 20, 3, '2025-10-26 17:02:58', 1),
(21, 21, 3, '2025-10-26 17:02:58', 1),
(22, 22, 3, '2025-10-26 17:02:58', 1),
(23, 23, 3, '2025-10-26 17:02:58', 1),
(24, 24, 3, '2025-10-26 17:02:58', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_test_attempts`
--

CREATE TABLE `user_test_attempts` (
  `attempt_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `test_id` int(11) NOT NULL,
  `attempt_number` int(11) NOT NULL,
  `start_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `submit_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `time_spent_seconds` int(11) DEFAULT NULL,
  `score` decimal(8,2) DEFAULT NULL,
  `percentage` decimal(5,2) DEFAULT NULL,
  `status` enum('IN_PROGRESS','SUBMITTED','GRADED','ABANDONED') DEFAULT 'IN_PROGRESS',
  `is_passed` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `user_test_attempts`
--

INSERT INTO `user_test_attempts` (`attempt_id`, `user_id`, `test_id`, `attempt_number`, `start_time`, `submit_time`, `time_spent_seconds`, `score`, `percentage`, `status`, `is_passed`) VALUES
(1, 15, 1, 1, '2024-04-05 03:00:00', '2024-04-05 03:25:00', 1500, 85.00, 85.00, 'GRADED', 1),
(2, 15, 3, 1, '2024-04-07 09:00:00', '2024-04-07 09:35:00', 2100, 78.00, 78.00, 'GRADED', 1),
(3, 16, 1, 1, '2024-04-03 02:30:00', '2024-04-03 02:55:00', 1500, 92.00, 92.00, 'GRADED', 1),
(4, 17, 1, 1, '2024-04-08 04:00:00', '2024-04-08 04:28:00', 1680, 88.00, 88.00, 'GRADED', 1),
(5, 17, 3, 1, '2024-04-08 08:45:00', '2024-04-08 09:20:00', 2100, 82.00, 82.00, 'GRADED', 1),
(6, 18, 4, 1, '2024-04-05 12:30:00', '2024-04-05 13:45:00', 4500, 145.00, 80.56, 'GRADED', 1),
(7, 18, 5, 1, '2024-04-08 12:00:00', '2024-04-08 12:45:00', 2700, 95.00, 79.17, 'GRADED', 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `classes`
--
ALTER TABLE `classes`
  ADD PRIMARY KEY (`class_id`),
  ADD UNIQUE KEY `class_code` (`class_code`),
  ADD KEY `course_id` (`course_id`),
  ADD KEY `manager_id` (`manager_id`),
  ADD KEY `idx_teacher_classes` (`teacher_id`),
  ADD KEY `idx_class_status` (`status`),
  ADD KEY `idx_class_teacher_status` (`teacher_id`,`status`);

--
-- Chỉ mục cho bảng `class_enrollments`
--
ALTER TABLE `class_enrollments`
  ADD PRIMARY KEY (`class_enrollment_id`),
  ADD UNIQUE KEY `unique_user_class` (`user_id`,`class_id`),
  ADD KEY `enrolled_by` (`enrolled_by`),
  ADD KEY `idx_class_students` (`class_id`,`status`);

--
-- Chỉ mục cho bảng `class_flashcards`
--
ALTER TABLE `class_flashcards`
  ADD PRIMARY KEY (`class_flashcard_id`),
  ADD KEY `class_id` (`class_id`),
  ADD KEY `flashcard_id` (`flashcard_id`);

--
-- Chỉ mục cho bảng `class_lessons`
--
ALTER TABLE `class_lessons`
  ADD PRIMARY KEY (`class_lesson_id`),
  ADD KEY `class_id` (`class_id`),
  ADD KEY `lesson_id` (`lesson_id`);

--
-- Chỉ mục cho bảng `class_sessions`
--
ALTER TABLE `class_sessions`
  ADD PRIMARY KEY (`session_id`),
  ADD KEY `lesson_id` (`lesson_id`),
  ADD KEY `idx_class_sessions` (`class_id`,`session_date`);

--
-- Chỉ mục cho bảng `class_tests`
--
ALTER TABLE `class_tests`
  ADD PRIMARY KEY (`class_test_id`),
  ADD KEY `class_id` (`class_id`),
  ADD KEY `test_id` (`test_id`);

--
-- Chỉ mục cho bảng `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`course_id`),
  ADD UNIQUE KEY `course_code` (`course_code`),
  ADD KEY `category_id` (`category_id`),
  ADD KEY `idx_course_status` (`status`,`is_active`),
  ADD KEY `idx_course_level` (`level`),
  ADD KEY `idx_created_by` (`created_by`),
  ADD KEY `idx_course_public_active` (`is_public`,`is_active`,`status`);

--
-- Chỉ mục cho bảng `course_categories`
--
ALTER TABLE `course_categories`
  ADD PRIMARY KEY (`category_id`),
  ADD KEY `parent_category_id` (`parent_category_id`);

--
-- Chỉ mục cho bảng `course_enrollments`
--
ALTER TABLE `course_enrollments`
  ADD PRIMARY KEY (`enrollment_id`),
  ADD UNIQUE KEY `unique_user_course` (`user_id`,`course_id`),
  ADD KEY `enrolled_by` (`enrolled_by`),
  ADD KEY `idx_user_enrollments` (`user_id`,`status`),
  ADD KEY `idx_course_enrollments` (`course_id`,`status`),
  ADD KEY `idx_enrollment_user_status` (`user_id`,`status`,`enrollment_date`);

--
-- Chỉ mục cho bảng `course_reviews`
--
ALTER TABLE `course_reviews`
  ADD PRIMARY KEY (`review_id`),
  ADD UNIQUE KEY `unique_user_course_review` (`course_id`,`user_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `idx_course_rating` (`course_id`,`rating`);

--
-- Chỉ mục cho bảng `course_settings`
--
ALTER TABLE `course_settings`
  ADD PRIMARY KEY (`setting_id`),
  ADD UNIQUE KEY `unique_course_setting` (`course_id`,`setting_key`);

--
-- Chỉ mục cho bảng `daily_study_logs`
--
ALTER TABLE `daily_study_logs`
  ADD PRIMARY KEY (`log_id`),
  ADD UNIQUE KEY `unique_user_date` (`user_id`,`study_date`),
  ADD KEY `idx_study_date` (`study_date`);

--
-- Chỉ mục cho bảng `flashcards`
--
ALTER TABLE `flashcards`
  ADD PRIMARY KEY (`flashcard_id`),
  ADD KEY `idx_course_flashcards` (`course_id`),
  ADD KEY `idx_user_flashcards` (`user_id`),
  ADD KEY `idx_difficulty` (`difficulty`);

--
-- Chỉ mục cho bảng `flashcard_lessons`
--
ALTER TABLE `flashcard_lessons`
  ADD PRIMARY KEY (`flashcard_lesson_id`),
  ADD KEY `course_id` (`course_id`),
  ADD KEY `lesson_id` (`lesson_id`);

--
-- Chỉ mục cho bảng `flashcard_lesson_items`
--
ALTER TABLE `flashcard_lesson_items`
  ADD PRIMARY KEY (`item_id`),
  ADD UNIQUE KEY `unique_lesson_flashcard` (`flashcard_lesson_id`,`flashcard_id`),
  ADD KEY `flashcard_id` (`flashcard_id`);

--
-- Chỉ mục cho bảng `lessons`
--
ALTER TABLE `lessons`
  ADD PRIMARY KEY (`lesson_id`),
  ADD UNIQUE KEY `unique_course_lesson` (`course_id`,`lesson_code`),
  ADD KEY `idx_lesson_order` (`course_id`,`lesson_order`);

--
-- Chỉ mục cho bảng `lesson_comments`
--
ALTER TABLE `lesson_comments`
  ADD PRIMARY KEY (`comment_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `parent_comment_id` (`parent_comment_id`),
  ADD KEY `idx_lesson_comments` (`lesson_id`,`created_at`);

--
-- Chỉ mục cho bảng `lesson_progress`
--
ALTER TABLE `lesson_progress`
  ADD PRIMARY KEY (`progress_id`),
  ADD UNIQUE KEY `unique_user_lesson` (`user_id`,`lesson_id`),
  ADD KEY `lesson_id` (`lesson_id`),
  ADD KEY `idx_user_progress` (`user_id`,`status`);

--
-- Chỉ mục cho bảng `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`notification_id`),
  ADD KEY `idx_user_notifications` (`user_id`,`is_read`,`created_at`);

--
-- Chỉ mục cho bảng `payment_transactions`
--
ALTER TABLE `payment_transactions`
  ADD PRIMARY KEY (`transaction_id`),
  ADD UNIQUE KEY `transaction_code` (`transaction_code`),
  ADD KEY `course_id` (`course_id`),
  ADD KEY `class_id` (`class_id`),
  ADD KEY `idx_user_payments` (`user_id`,`status`),
  ADD KEY `idx_transaction_status` (`status`,`payment_date`);

--
-- Chỉ mục cho bảng `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`question_id`),
  ADD KEY `question_type_id` (`question_type_id`),
  ADD KEY `created_by` (`created_by`),
  ADD KEY `idx_course_questions` (`course_id`),
  ADD KEY `idx_difficulty` (`difficulty`);

--
-- Chỉ mục cho bảng `question_options`
--
ALTER TABLE `question_options`
  ADD PRIMARY KEY (`option_id`),
  ADD KEY `idx_question_options` (`question_id`);

--
-- Chỉ mục cho bảng `question_types`
--
ALTER TABLE `question_types`
  ADD PRIMARY KEY (`type_id`),
  ADD UNIQUE KEY `type_name` (`type_name`);

--
-- Chỉ mục cho bảng `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`role_id`),
  ADD UNIQUE KEY `role_name` (`role_name`);

--
-- Chỉ mục cho bảng `session_attendance`
--
ALTER TABLE `session_attendance`
  ADD PRIMARY KEY (`attendance_id`),
  ADD UNIQUE KEY `unique_session_user` (`session_id`,`user_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `marked_by` (`marked_by`);

--
-- Chỉ mục cho bảng `social_auth_accounts`
--
ALTER TABLE `social_auth_accounts`
  ADD PRIMARY KEY (`social_auth_id`),
  ADD UNIQUE KEY `unique_provider_user` (`provider`,`provider_user_id`),
  ADD KEY `idx_provider_email` (`provider`,`provider_email`),
  ADD KEY `idx_user_provider` (`user_id`,`provider`);

--
-- Chỉ mục cho bảng `study_streaks`
--
ALTER TABLE `study_streaks`
  ADD PRIMARY KEY (`streak_id`),
  ADD UNIQUE KEY `unique_user_streak` (`user_id`);

--
-- Chỉ mục cho bảng `system_settings`
--
ALTER TABLE `system_settings`
  ADD PRIMARY KEY (`setting_id`),
  ADD UNIQUE KEY `setting_key` (`setting_key`);

--
-- Chỉ mục cho bảng `tests`
--
ALTER TABLE `tests`
  ADD PRIMARY KEY (`test_id`),
  ADD UNIQUE KEY `unique_course_test` (`course_id`,`test_code`),
  ADD KEY `created_by` (`created_by`),
  ADD KEY `idx_available_tests` (`is_active`,`available_from`,`available_until`);

--
-- Chỉ mục cho bảng `test_questions`
--
ALTER TABLE `test_questions`
  ADD PRIMARY KEY (`test_question_id`),
  ADD UNIQUE KEY `unique_test_question` (`test_id`,`question_id`),
  ADD KEY `question_id` (`question_id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `idx_email` (`email`),
  ADD KEY `idx_username` (`username`),
  ADD KEY `idx_is_active` (`is_active`),
  ADD KEY `idx_auth_provider` (`auth_provider`);

--
-- Chỉ mục cho bảng `user_activity_logs`
--
ALTER TABLE `user_activity_logs`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `idx_user_activity` (`user_id`,`created_at`),
  ADD KEY `idx_activity_type` (`activity_type`);

--
-- Chỉ mục cho bảng `user_answers`
--
ALTER TABLE `user_answers`
  ADD PRIMARY KEY (`answer_id`),
  ADD UNIQUE KEY `unique_attempt_question` (`attempt_id`,`question_id`),
  ADD KEY `question_id` (`question_id`),
  ADD KEY `selected_option_id` (`selected_option_id`);

--
-- Chỉ mục cho bảng `user_flashcard_progress`
--
ALTER TABLE `user_flashcard_progress`
  ADD PRIMARY KEY (`progress_id`),
  ADD UNIQUE KEY `unique_user_flashcard` (`user_id`,`flashcard_id`),
  ADD KEY `flashcard_id` (`flashcard_id`),
  ADD KEY `idx_next_review` (`user_id`,`next_review_at`),
  ADD KEY `idx_flashcard_review` (`user_id`,`next_review_at`);

--
-- Chỉ mục cho bảng `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_role_id`),
  ADD UNIQUE KEY `unique_user_role` (`user_id`,`role_id`),
  ADD KEY `role_id` (`role_id`),
  ADD KEY `assigned_by` (`assigned_by`);

--
-- Chỉ mục cho bảng `user_test_attempts`
--
ALTER TABLE `user_test_attempts`
  ADD PRIMARY KEY (`attempt_id`),
  ADD KEY `test_id` (`test_id`),
  ADD KEY `idx_user_attempts` (`user_id`,`test_id`),
  ADD KEY `idx_attempt_status` (`status`),
  ADD KEY `idx_test_attempts_score` (`user_id`,`test_id`,`score`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `classes`
--
ALTER TABLE `classes`
  MODIFY `class_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `class_enrollments`
--
ALTER TABLE `class_enrollments`
  MODIFY `class_enrollment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT cho bảng `class_flashcards`
--
ALTER TABLE `class_flashcards`
  MODIFY `class_flashcard_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `class_lessons`
--
ALTER TABLE `class_lessons`
  MODIFY `class_lesson_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `class_sessions`
--
ALTER TABLE `class_sessions`
  MODIFY `session_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT cho bảng `class_tests`
--
ALTER TABLE `class_tests`
  MODIFY `class_test_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `courses`
--
ALTER TABLE `courses`
  MODIFY `course_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `course_categories`
--
ALTER TABLE `course_categories`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `course_enrollments`
--
ALTER TABLE `course_enrollments`
  MODIFY `enrollment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `course_reviews`
--
ALTER TABLE `course_reviews`
  MODIFY `review_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `course_settings`
--
ALTER TABLE `course_settings`
  MODIFY `setting_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `daily_study_logs`
--
ALTER TABLE `daily_study_logs`
  MODIFY `log_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `flashcards`
--
ALTER TABLE `flashcards`
  MODIFY `flashcard_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT cho bảng `flashcard_lessons`
--
ALTER TABLE `flashcard_lessons`
  MODIFY `flashcard_lesson_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `flashcard_lesson_items`
--
ALTER TABLE `flashcard_lesson_items`
  MODIFY `item_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `lessons`
--
ALTER TABLE `lessons`
  MODIFY `lesson_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT cho bảng `lesson_comments`
--
ALTER TABLE `lesson_comments`
  MODIFY `comment_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `lesson_progress`
--
ALTER TABLE `lesson_progress`
  MODIFY `progress_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT cho bảng `notifications`
--
ALTER TABLE `notifications`
  MODIFY `notification_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `payment_transactions`
--
ALTER TABLE `payment_transactions`
  MODIFY `transaction_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `questions`
--
ALTER TABLE `questions`
  MODIFY `question_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `question_options`
--
ALTER TABLE `question_options`
  MODIFY `option_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT cho bảng `question_types`
--
ALTER TABLE `question_types`
  MODIFY `type_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `roles`
--
ALTER TABLE `roles`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `session_attendance`
--
ALTER TABLE `session_attendance`
  MODIFY `attendance_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `social_auth_accounts`
--
ALTER TABLE `social_auth_accounts`
  MODIFY `social_auth_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `study_streaks`
--
ALTER TABLE `study_streaks`
  MODIFY `streak_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `system_settings`
--
ALTER TABLE `system_settings`
  MODIFY `setting_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `tests`
--
ALTER TABLE `tests`
  MODIFY `test_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `test_questions`
--
ALTER TABLE `test_questions`
  MODIFY `test_question_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT cho bảng `user_activity_logs`
--
ALTER TABLE `user_activity_logs`
  MODIFY `log_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `user_answers`
--
ALTER TABLE `user_answers`
  MODIFY `answer_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `user_flashcard_progress`
--
ALTER TABLE `user_flashcard_progress`
  MODIFY `progress_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `user_roles`
--
ALTER TABLE `user_roles`
  MODIFY `user_role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT cho bảng `user_test_attempts`
--
ALTER TABLE `user_test_attempts`
  MODIFY `attempt_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `classes`
--
ALTER TABLE `classes`
  ADD CONSTRAINT `classes_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`),
  ADD CONSTRAINT `classes_ibfk_2` FOREIGN KEY (`teacher_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `classes_ibfk_3` FOREIGN KEY (`manager_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL;

--
-- Các ràng buộc cho bảng `class_enrollments`
--
ALTER TABLE `class_enrollments`
  ADD CONSTRAINT `class_enrollments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `class_enrollments_ibfk_2` FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `class_enrollments_ibfk_3` FOREIGN KEY (`enrolled_by`) REFERENCES `users` (`user_id`) ON DELETE SET NULL;

--
-- Các ràng buộc cho bảng `class_flashcards`
--
ALTER TABLE `class_flashcards`
  ADD CONSTRAINT `class_flashcards_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `class_flashcards_ibfk_2` FOREIGN KEY (`flashcard_id`) REFERENCES `flashcards` (`flashcard_id`) ON DELETE SET NULL;

--
-- Các ràng buộc cho bảng `class_lessons`
--
ALTER TABLE `class_lessons`
  ADD CONSTRAINT `class_lessons_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `class_lessons_ibfk_2` FOREIGN KEY (`lesson_id`) REFERENCES `lessons` (`lesson_id`) ON DELETE SET NULL;

--
-- Các ràng buộc cho bảng `class_sessions`
--
ALTER TABLE `class_sessions`
  ADD CONSTRAINT `class_sessions_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `class_sessions_ibfk_2` FOREIGN KEY (`lesson_id`) REFERENCES `lessons` (`lesson_id`) ON DELETE SET NULL;

--
-- Các ràng buộc cho bảng `class_tests`
--
ALTER TABLE `class_tests`
  ADD CONSTRAINT `class_tests_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `class_tests_ibfk_2` FOREIGN KEY (`test_id`) REFERENCES `tests` (`test_id`) ON DELETE SET NULL;

--
-- Các ràng buộc cho bảng `courses`
--
ALTER TABLE `courses`
  ADD CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `course_categories` (`category_id`) ON DELETE SET NULL,
  ADD CONSTRAINT `courses_ibfk_2` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`);

--
-- Các ràng buộc cho bảng `course_categories`
--
ALTER TABLE `course_categories`
  ADD CONSTRAINT `course_categories_ibfk_1` FOREIGN KEY (`parent_category_id`) REFERENCES `course_categories` (`category_id`) ON DELETE SET NULL;

--
-- Các ràng buộc cho bảng `course_enrollments`
--
ALTER TABLE `course_enrollments`
  ADD CONSTRAINT `course_enrollments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `course_enrollments_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `course_enrollments_ibfk_3` FOREIGN KEY (`enrolled_by`) REFERENCES `users` (`user_id`) ON DELETE SET NULL;

--
-- Các ràng buộc cho bảng `course_reviews`
--
ALTER TABLE `course_reviews`
  ADD CONSTRAINT `course_reviews_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `course_reviews_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `course_settings`
--
ALTER TABLE `course_settings`
  ADD CONSTRAINT `course_settings_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `daily_study_logs`
--
ALTER TABLE `daily_study_logs`
  ADD CONSTRAINT `daily_study_logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `flashcards`
--
ALTER TABLE `flashcards`
  ADD CONSTRAINT `flashcards_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `flashcards_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `flashcard_lessons`
--
ALTER TABLE `flashcard_lessons`
  ADD CONSTRAINT `flashcard_lessons_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `flashcard_lessons_ibfk_2` FOREIGN KEY (`lesson_id`) REFERENCES `lessons` (`lesson_id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `flashcard_lesson_items`
--
ALTER TABLE `flashcard_lesson_items`
  ADD CONSTRAINT `flashcard_lesson_items_ibfk_1` FOREIGN KEY (`flashcard_lesson_id`) REFERENCES `flashcard_lessons` (`flashcard_lesson_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `flashcard_lesson_items_ibfk_2` FOREIGN KEY (`flashcard_id`) REFERENCES `flashcards` (`flashcard_id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `lessons`
--
ALTER TABLE `lessons`
  ADD CONSTRAINT `lessons_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `lesson_comments`
--
ALTER TABLE `lesson_comments`
  ADD CONSTRAINT `lesson_comments_ibfk_1` FOREIGN KEY (`lesson_id`) REFERENCES `lessons` (`lesson_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `lesson_comments_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `lesson_comments_ibfk_3` FOREIGN KEY (`parent_comment_id`) REFERENCES `lesson_comments` (`comment_id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `lesson_progress`
--
ALTER TABLE `lesson_progress`
  ADD CONSTRAINT `lesson_progress_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `lesson_progress_ibfk_2` FOREIGN KEY (`lesson_id`) REFERENCES `lessons` (`lesson_id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `payment_transactions`
--
ALTER TABLE `payment_transactions`
  ADD CONSTRAINT `payment_transactions_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `payment_transactions_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE SET NULL,
  ADD CONSTRAINT `payment_transactions_ibfk_3` FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`) ON DELETE SET NULL;

--
-- Các ràng buộc cho bảng `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `questions_ibfk_2` FOREIGN KEY (`question_type_id`) REFERENCES `question_types` (`type_id`),
  ADD CONSTRAINT `questions_ibfk_3` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`);

--
-- Các ràng buộc cho bảng `question_options`
--
ALTER TABLE `question_options`
  ADD CONSTRAINT `question_options_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `session_attendance`
--
ALTER TABLE `session_attendance`
  ADD CONSTRAINT `session_attendance_ibfk_1` FOREIGN KEY (`session_id`) REFERENCES `class_sessions` (`session_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `session_attendance_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `session_attendance_ibfk_3` FOREIGN KEY (`marked_by`) REFERENCES `users` (`user_id`) ON DELETE SET NULL;

--
-- Các ràng buộc cho bảng `social_auth_accounts`
--
ALTER TABLE `social_auth_accounts`
  ADD CONSTRAINT `social_auth_accounts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `study_streaks`
--
ALTER TABLE `study_streaks`
  ADD CONSTRAINT `study_streaks_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `tests`
--
ALTER TABLE `tests`
  ADD CONSTRAINT `tests_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `tests_ibfk_2` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`);

--
-- Các ràng buộc cho bảng `test_questions`
--
ALTER TABLE `test_questions`
  ADD CONSTRAINT `test_questions_ibfk_1` FOREIGN KEY (`test_id`) REFERENCES `tests` (`test_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `test_questions_ibfk_2` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `user_activity_logs`
--
ALTER TABLE `user_activity_logs`
  ADD CONSTRAINT `user_activity_logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL;

--
-- Các ràng buộc cho bảng `user_answers`
--
ALTER TABLE `user_answers`
  ADD CONSTRAINT `user_answers_ibfk_1` FOREIGN KEY (`attempt_id`) REFERENCES `user_test_attempts` (`attempt_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_answers_ibfk_2` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_answers_ibfk_3` FOREIGN KEY (`selected_option_id`) REFERENCES `question_options` (`option_id`) ON DELETE SET NULL;

--
-- Các ràng buộc cho bảng `user_flashcard_progress`
--
ALTER TABLE `user_flashcard_progress`
  ADD CONSTRAINT `user_flashcard_progress_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_flashcard_progress_ibfk_2` FOREIGN KEY (`flashcard_id`) REFERENCES `flashcards` (`flashcard_id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `user_roles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_roles_ibfk_3` FOREIGN KEY (`assigned_by`) REFERENCES `users` (`user_id`) ON DELETE SET NULL;

--
-- Các ràng buộc cho bảng `user_test_attempts`
--
ALTER TABLE `user_test_attempts`
  ADD CONSTRAINT `user_test_attempts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_test_attempts_ibfk_2` FOREIGN KEY (`test_id`) REFERENCES `tests` (`test_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
